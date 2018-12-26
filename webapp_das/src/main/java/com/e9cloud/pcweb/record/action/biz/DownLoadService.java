package com.e9cloud.pcweb.record.action.biz;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.ReflectionUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.Condition;
import com.e9cloud.mongodb.domain.DownLoadWrapper;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */
@Service
public class DownLoadService {
    private static final Logger logger = LoggerFactory.getLogger(DownLoadService.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CommonService commonSevice;

    @Autowired
    private AppService appService;

    @Autowired
    private MongoDBDao mongoDBDao;

    public void genRecord(DownLoadWrapper wrapper) {
        String path = appConfig.getTempRecordFilePath() + Tools.formatDate(new Date(), "yyyyMM") + "/";
        File dirs = new File(path);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            String filePath = path + wrapper.getFileName();
//            byte[] utf8=filePath.getBytes("utf-8");
//            String dpath = new String(utf8,"gbk");
            File txtFile = new File(filePath + ".csv");
            if(txtFile.exists()){
                txtFile.delete();
                txtFile.createNewFile();
            }
            logger.info(wrapper.getFileName());
            List<AppInfo> appList = appService.findAllApp();
            Map appMap = Tools.listToMap(appList, "appid", "appName", AppInfo.class);
            fos = new FileOutputStream(txtFile);
            osw = new OutputStreamWriter(fos, "GBK");
            bw = new BufferedWriter(osw);
            boolean flag = false;
            DownLoadWrapper.Business business = wrapper.getBusiness(wrapper.getSource());
            DownLoadWrapper.Fields fields = wrapper.getFields(wrapper.getSource());
            int total = wrapper.getPageCount();

            Page page = new Page();
            page.setPageSize(5000);
            page.setTotal(total);

            // 排序
            DownLoadWrapper.OrderBy orderBy = wrapper.getOrderBy(wrapper.getSource());
            page.setSidx(orderBy.getSidx());
            page.setSord(orderBy.getSord());

            int pages = page.getTotalPage();
            String condstr = wrapper.getConds();
            if (Tools.isNotNullStr(condstr)) {
                Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
                if (Tools.isNotNullArray(conditions)) {
                    page.setConds(Arrays.asList(conditions));
                }
            }

            if (Tools.isNotNullStr(wrapper.getFeeid())) { // 加上feeid
                page.addCond(new Condition("feeid", wrapper.getFeeid()));
            }

            PageWrapper pageWrapper;
            for (int i = 1; i <= pages; i++) {
                page.setPage(i);
                page.calcPageCount();
                pageWrapper = mongoDBDao.page(page, business.getEntityClass(), business.getCollection());

                List list = pageWrapper.getRows();
                flag = recordToTxt(wrapper.getFileName(), list,fields, bw, appMap,i);
            }
            if (flag) {
                DownLoadWrapper dbWrapper = new DownLoadWrapper();
                dbWrapper.setId(wrapper.getId());
                dbWrapper.setStatus("01");
                dbWrapper.setFilePath(Tools.formatDate(new Date(), "yyyyMM") + "/" + wrapper.getFileName() + ".csv");
                commonSevice.updateDownLoadInfo(dbWrapper);
            }
        } catch (Exception e) {
            logger.error("error", e);
        } finally {
            closeIO(bw);
            closeIO(osw);
            closeIO(fos);
        }

    }

    private boolean recordToTxt(String fileName, List list,DownLoadWrapper.Fields fields, BufferedWriter bw, Map appMap,int page) {
        boolean flag = true;
        try {
            if(page==1) {
                StringBuffer title = new StringBuffer();
                List<String> filedNameList = Tools.stringToList(fields.getFieldName(), "|");
                for (int i = 0; i < filedNameList.size(); i++) {
                    if (i == filedNameList.size() - 1) {
                        title.append(filedNameList.get(i)).append("\r\n");
                    } else {
                        title.append(filedNameList.get(i)).append(",");
                    }
                }
                bw.write(title.toString());
            }
            for (Object obj : list) {
                StringBuffer context = new StringBuffer();
                List<String> filedList = Tools.stringToList(fields.getFieldV(), "|");
                for (int i = 0; i < filedList.size(); i++) {
                    String filedV = filedList.get(i);
                    boolean isLast = false;
                    if (i == filedList.size() - 1) {
                        isLast = true;
                    }
                    getConversionInfo(filedV, appMap, fields, obj, context, isLast);
                }
                bw.write(context.toString());
            }
            bw.flush();
        } catch (Exception e) {
            logger.error("fileName {} toTxt find error", fileName, e);
            flag = false;
        }
        return flag;
    }

    private void closeIO(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getConversionInfo(String filedV, Map<String, String> map, DownLoadWrapper.Fields fields, Object obj, StringBuffer context, boolean isLast) {
        String filed;
        if (filedV.contains("@")) {
            filed = filedV.split("@")[1];
        } else {
            filed = filedV;
        }
        if (filedV.equals("appName@appid")) {
            String appidV = (String) ReflectionUtils.getFieldValue(obj, filed);
            if (map.containsKey(appidV)) {
                String appName = map.get(appidV);
                context.append(appName);
            }
        } else if (filed.equals("abLine") && fields.getCode().equals("01")) {
            String abLine = (String) ReflectionUtils.getFieldValue(obj, filed);
            context.append("A".equals(abLine) ? "主叫" : "被叫");
        } else if (filed.equals("abLine") && fields.getCode().equals("06")) {
            String abLine = (String) ReflectionUtils.getFieldValue(obj, filed);
            if ("A".equals(abLine) || "B".equals(abLine)) {
                context.append(abLine);
            } else {
                context.append("");
            }
        } else if (filed.equals("abLine") && fields.getCode().equals("03")) {
            String abLine = (String) ReflectionUtils.getFieldValue(obj, filed);
            if ("A".equals(abLine) || "B".equals(abLine)) {
                context.append("回拨").append(",").append(abLine);
            } else if ("C".equals(abLine)) {
                context.append("回呼").append(",");
            } else {
                context.append(",,");
            }
        } else if (filed.equals("abLine") && fields.getCode().equals("04")) {
            String abLine = (String) ReflectionUtils.getFieldValue(obj, filed);
            context.append("I".equals(abLine) ? "呼入" : "呼出");
        } else if (filed.equals("connectSucc")) {
            String connectSucc = String.valueOf(ReflectionUtils.getFieldValue(obj, filed));
            context.append("1".equals(connectSucc) ? "已接通" : "未接通");
        } else if (filed.equals("operator") && fields.getCode().equals("04")) {
            String operator = String.valueOf(ReflectionUtils.getFieldValue(obj, filed));
            if ("00".equals(operator)) {
                context.append("移动");
            } else if ("01".equals(operator)) {
                context.append("联通");
            } else if ("02".equals(operator)) {
                context.append("电信");
            } else if ("06".equals(operator)) {
                context.append("其他");
            } else {
                context.append("其他");
            }
        } else if (filed.equals("rcdType") && fields.getCode().equals("06")) {
            String rcdType = String.valueOf(ReflectionUtils.getFieldValue(obj, filed));
            if ("sipprest".equals(rcdType)) {
                context.append("回拨");
            } else if ("sippout".equals(rcdType)) {
                context.append("直拨");
            } else if ("sippin".equals(rcdType)) {
                context.append("回呼");
            }
        } else if (filed.equals("rcdType") && fields.getCode().equals("07")) {
            String rcdType = String.valueOf(ReflectionUtils.getFieldValue(obj, filed));
            if ("CallInSip".equals(rcdType)) {
                context.append("呼入总机SIP");
            } else if ("CallInNonSip".equals(rcdType)) {
                context.append("呼入总机非SIP");
            } else if ("CallInDirect".equals(rcdType)) {
                context.append("呼入直呼");
            }else if ("CallOutLocal".equals(rcdType)) {
                context.append("呼出市话");
            }else if ("CallOutNonLocal".equals(rcdType)) {
                context.append("呼出长途");
            }
        }else if (filed.equals("releasedir")) {
            String releasedir = String.valueOf(ReflectionUtils.getFieldValue(obj, filed));
            if("0".equals(releasedir)){
                context.append("系统");
            }else if("1".equals(releasedir)){
                context.append("主叫");
            }else if("2".equals(releasedir)){
                context.append("被叫");
            }
        }  else {
            Object value = ReflectionUtils.getFieldValue(obj, filed);
            if (value instanceof Date) {
                String dateV = Tools.formatDate((Date) value);
                context.append(dateV);
            } else if (value instanceof Double) {
                context.append(Double.toString((Double) value));
            } else if (value instanceof Float) {
                context.append(Float.toString((Float) value));
            } else if (value instanceof Integer) {
                context.append(Integer.toString((Integer) value));
            } else if (value instanceof Boolean) {
                context.append(Boolean.toString((Boolean) value));
            } else {
                context.append((String) value);
            }
        }
        if (isLast) {
            context.append("\r\n");
        } else {
            context.append(",");
        }
    }

}
