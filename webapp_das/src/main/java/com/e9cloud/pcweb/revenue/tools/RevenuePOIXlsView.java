package com.e9cloud.pcweb.revenue.tools;

import com.e9cloud.core.util.Tools;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 封装了有关excel表格导出的相关操作。(xls)
 * @author wzj 2016-01-22
 *
 */
public class RevenuePOIXlsView extends AbstractXlsView {

    private int WIDTH = 256;
    private int WIDTH_20 = 20 * 256;

    private int HIGHT_20 = 20 * 20;
    private int HIGHT_25 = 25 * 20;

    /** 标题 */
    private String title;

    /*model.addAttribute("ym", ym); // 月份
        model.addAttribute("feeid", feeid); // 月份
        model.addAttribute("month", details.get("month")); // 客户信息

        model.addAttribute("rests", details.get("rest")); // rest 专线语音
        model.addAttribute("masks", details.get("mask")); // mask 专号通
        model.addAttribute("sips", details.get("sip")); // sip sip接口
        model.addAttribute("ccs", details.get("cc")); // cc 智能云调度
        model.addAttribute("sps", details.get("sp")); // sp 云话机*/

    /**
     * Application-provided subclasses must implement this method to populate
     * the Excel workbook document, given the model.
     *
     * @param model    the model Map
     * @param workbook the Excel workbook to populate
     * @param request  in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HSSFWorkbook wb = (HSSFWorkbook)workbook;
        String excelName = (String)model.get("excelName") + ".xls";
        title = "消费月账单" + model.get("ym");
        /*****************************************************/
        //添加序号列

        // 设置response方式,使执行此controller时??自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");
        if(request.getHeader( "USER-AGENT" ).toLowerCase().indexOf( "firefox" ) >  0 ){
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(excelName.getBytes("GB2312"),"ISO-8859-1"));
        }else{
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
        }

        //创建第一个工作表
        HSSFSheet ws = wb.createSheet("Sheet1"); // sheet名称

        writeTitle(ws, wb); // 标题
        writeMonthContext(ws, wb, getMap(model, ("month")));
        writeRestContext(ws, wb, getMap(model, "rest"));
        writeMaskContext(ws, wb, getMap(model, "mask"));
        writeSipContext(ws, wb, getMap(model, "sip"));
        writeCcContext(ws, wb, getMap(model, "cc"));
        writeSpContext(ws, wb, getMap(model, "sp"));
        writeEccContext(ws, wb, getMap(model, "ecc"));

        setStyle(ws, wb);


    }

    private int rowcnt = 0; // 行计数器

    //添加表头
    private void writeTitle(HSSFSheet hssfSheet, HSSFWorkbook wb) {

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 16);//字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font.setFontName("宋体");

        HSSFCellStyle cellStyle = wb.createCellStyle();//title 的样式
        //cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        //cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        //cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框

        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中

        HSSFRow rowHeader = hssfSheet.createRow(rowcnt);
        rowHeader.setHeight((short)HIGHT_25); // 行高

        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellStyle(cellStyle);
        cellHeader.setCellValue(title);
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param month		表数据。
     */
    private void writeMonthContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> month) {
        if (month == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("客户名称：" + month.get("companyName") + "        客户账号：" + month.get("sid"));
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("本期应收账款（元）");
        rowtitle.createCell(1).setCellValue("本月充值金额（元）");
        rowtitle.createCell(2).setCellValue("期初余额（元）");
        rowtitle.createCell(3).setCellValue("账户余额（元）");
        rowtitle.createCell(4).setCellValue("累计消费总额（元）");

        HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
        rowbody.createCell(0).setCellValue(Tools.toStr(month.get("fee")));
        rowbody.createCell(1).setCellValue(Tools.toStr(month.get("recharge")));
        rowbody.createCell(2).setCellValue(Tools.toStr(month.get("pbalance")));
        rowbody.createCell(3).setCellValue(Tools.toStr(month.get("bbalance")));
        rowbody.createCell(4).setCellValue(Tools.toStr(month.get("tfee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param rests		表数据。
     */
    private void writeRestContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> rests) {
        if (rests == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("专线语音");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(2).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(3).setCellValue("计费录音时长（分钟）");
        rowtitle.createCell(4).setCellValue("通话费用（元）");
        rowtitle.createCell(5).setCellValue("录音费用（元）");
        rowtitle.createCell(6).setCellValue("合计（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) rests.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.decode(row.get("abline"), "A", "A路", "B", "B路"));
            rowbody.createCell(1).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("jfscsum")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("jflyscsum")));
            rowbody.createCell(4).setCellValue(Tools.toStr(row.get("fee")));
            rowbody.createCell(5).setCellValue(Tools.toStr(row.get("recordingfee")));
            rowbody.createCell(6).setCellValue(Tools.toStr(row.get("tfee")));
        }

        Map<String, Object> total = (Map<String, Object>) rests.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(1).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("jfscsum")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("jflyscsum")));
        rowfoot.createCell(4).setCellValue(Tools.toStr(total.get("fee")));
        rowfoot.createCell(5).setCellValue(Tools.toStr(total.get("recordingfee")));
        rowfoot.createCell(6).setCellValue(Tools.toStr(total.get("tfee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param rests		表数据。
     */
    private void writeMaskContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> rests) {
        if (rests == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("专号通");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(2).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(3).setCellValue("计费录音时长（分钟）");
        rowtitle.createCell(4).setCellValue("通话费用（元）");
        rowtitle.createCell(5).setCellValue("录音费用（元）");
        rowtitle.createCell(6).setCellValue("合计（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) rests.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.decode(row.get("abline"), "A", "A路", "B", "B路", "C", "回呼"));
            rowbody.createCell(1).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("jfscsum")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("jflyscsum")));
            rowbody.createCell(4).setCellValue(Tools.toStr(row.get("fee")));
            rowbody.createCell(5).setCellValue(Tools.toStr(row.get("recordingfee")));
            rowbody.createCell(6).setCellValue(Tools.toStr(row.get("tfee")));
        }

        Object rent = rests.get("rent");
        if (rent != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("号码月租");
            rowrent.createCell(6).setCellValue(Tools.toStr(rent));
        }

        Map<String, Object> total = (Map<String, Object>) rests.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(1).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("jfscsum")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("jflyscsum")));
        rowfoot.createCell(4).setCellValue(Tools.toStr(total.get("fee")));
        rowfoot.createCell(5).setCellValue(Tools.toStr(total.get("recordingfee")));
        rowfoot.createCell(6).setCellValue(Tools.toStr(total.get("tfee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param sips		表数据。
     */
    private void writeSipContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> sips) {
        if (sips == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("SIP接口");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(2).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(3).setCellValue("通话费用（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) sips.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.toStr(row.get("subName")));
            rowbody.createCell(1).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("jfscsum60")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("fee")));
        }

        Map<String, Object> total = (Map<String, Object>) sips.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(1).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("jfscsum60")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("fee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param ccs		表数据。
     */
    private void writeCcContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> ccs) {
        if (ccs == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("智能云调度");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("运营商");
        rowtitle.createCell(2).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(3).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(4).setCellValue("通话费用（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) ccs.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.decode(row.get("abline"), "I", "呼入", "O", "呼出"));
            rowbody.createCell(1).setCellValue(Tools.decode(row.get("operator"), "00", "移动", "01", "联通", "02", "电信",  "其他"));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("jfscsum")));
            rowbody.createCell(4).setCellValue(Tools.toStr(row.get("fee")));
        }

        Object rent = ccs.get("rent");
        if (rent != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("线路月租");
            rowrent.createCell(4).setCellValue(Tools.toStr(rent));
        }

        Map<String, Object> total = (Map<String, Object>) ccs.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("jfscsum")));
        rowfoot.createCell(4).setCellValue(Tools.toStr(total.get("fee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param sps		表数据。
     */
    private void writeSpContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> sps) {
        if (sps == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("云话机");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(2).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(3).setCellValue("计费录音时长（分钟）");
        rowtitle.createCell(4).setCellValue("通话费用（元）");
        rowtitle.createCell(5).setCellValue("录音费用（元）");
        rowtitle.createCell(6).setCellValue("合计（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) sps.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.decode(row.get("type"), "restA", "回拨A路", "restB", "回拨B路", "sipA", "Sipphone间回拨A", "sipB", "Sipphone间回拨B", "restO", "直拨", "sipO", "Sipphone间直拨", "C", "回呼"));
            rowbody.createCell(1).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("jfscsum")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("jflyscsum")));
            rowbody.createCell(4).setCellValue(Tools.toStr(row.get("fee")));
            rowbody.createCell(5).setCellValue(Tools.toStr(row.get("recordingfee")));
            rowbody.createCell(6).setCellValue(Tools.toStr(row.get("tfee")));
        }

        Object rent = sps.get("rent");
        if (rent != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("号码月租");
            rowrent.createCell(6).setCellValue(Tools.toStr(rent));
        }

        Object minconsume = sps.get("minconsume");
        if (minconsume != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("低消补扣");
            rowrent.createCell(6).setCellValue(Tools.toStr(minconsume));
        }

        Map<String, Object> total = (Map<String, Object>) sps.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(1).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("jfscsum")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("jflyscsum")));
        rowfoot.createCell(4).setCellValue(Tools.toStr(total.get("fee")));
        rowfoot.createCell(5).setCellValue(Tools.toStr(total.get("recordingfee")));
        rowfoot.createCell(6).setCellValue(Tools.toStr(total.get("tfee")));
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param eccs		表数据。
     */
    private void writeEccContext(HSSFSheet hssfSheet, HSSFWorkbook wb, Map<String, Object> eccs) {
        if (eccs == null) return;

        HSSFRow rowHeader = hssfSheet.createRow(++rowcnt);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("云总机");
        hssfSheet.addMergedRegion(new CellRangeAddress(rowcnt, rowcnt, 0, 6)); // 合并单元格

        HSSFRow rowtitle = hssfSheet.createRow(++rowcnt);
        rowtitle.createCell(0).setCellValue("类型");
        rowtitle.createCell(1).setCellValue("累计通话时长（秒）");
        rowtitle.createCell(2).setCellValue("计费通话时长（分钟）");
        rowtitle.createCell(3).setCellValue("计费录音时长（分钟）");
        rowtitle.createCell(4).setCellValue("通话费用（元）");
        rowtitle.createCell(5).setCellValue("录音费用（元）");
        rowtitle.createCell(6).setCellValue("合计（元）");

        List<Map<String, Object>> rows = (List<Map<String, Object>>) eccs.get("rows");
        for (Map<String, Object> row : rows) {
            HSSFRow rowbody = hssfSheet.createRow(++rowcnt);
            rowbody.createCell(0).setCellValue(Tools.decode(row.get("type"), "CallInSip", "呼入总机SIP", "CallInNonSip", "呼入总机非SIP", "CallInDirect", "呼入直呼", "CallOutLocal", "呼出市话", "CallOutNonLocal", "呼出长途"));
            rowbody.createCell(1).setCellValue(Tools.toStr(row.get("thscsum")));
            rowbody.createCell(2).setCellValue(Tools.toStr(row.get("jfscsum")));
            rowbody.createCell(3).setCellValue(Tools.toStr(row.get("jflyscsum")));
            rowbody.createCell(4).setCellValue(Tools.toStr(row.get("fee")));
            rowbody.createCell(5).setCellValue(Tools.toStr(row.get("recordingfee")));
            rowbody.createCell(6).setCellValue(Tools.toStr(row.get("tfee")));
        }

        Object zjrent = eccs.get("zjrent");
        if (zjrent != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("总机月租");
            rowrent.createCell(6).setCellValue(Tools.toStr(zjrent));
        }

        Object rent = eccs.get("rent");
        if (rent != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("SIP号码月租");
            rowrent.createCell(6).setCellValue(Tools.toStr(rent));
        }

        Object minconsume = eccs.get("minconsume");
        if (minconsume != null) {
            HSSFRow rowrent = hssfSheet.createRow(++rowcnt);
            rowrent.createCell(0).setCellValue("低消补扣");
            rowrent.createCell(6).setCellValue(Tools.toStr(minconsume));
        }

        Map<String, Object> total = (Map<String, Object>) eccs.get("total");
        HSSFRow rowfoot = hssfSheet.createRow(++rowcnt);
        rowfoot.createCell(0).setCellValue("总计");
        rowfoot.createCell(1).setCellValue(Tools.toStr(total.get("thscsum")));
        rowfoot.createCell(2).setCellValue(Tools.toStr(total.get("jfscsum")));
        rowfoot.createCell(3).setCellValue(Tools.toStr(total.get("jflyscsum")));
        rowfoot.createCell(4).setCellValue(Tools.toStr(total.get("fee")));
        rowfoot.createCell(5).setCellValue(Tools.toStr(total.get("recordingfee")));
        rowfoot.createCell(6).setCellValue(Tools.toStr(total.get("tfee")));
    }

    // 设置样式
    private void setStyle(HSSFSheet sheet, HSSFWorkbook wb){
        // 设置列宽
        for (int i=0; i<7; i++) {
            sheet.setColumnWidth(i, WIDTH_20);
        }

        for (int i = 1; i <= rowcnt; i++) {
            sheet.getRow(i).setHeight((short) HIGHT_20);
        }

        /*HSSFCellStyle cellStyle = wb.createCellStyle();//title 的样式
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框

        for (int i=0; i<=rowcnt; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j=0; j<=rowcnt; j++) {
                logger.info("row:" + i + ",col:" + j);
                HSSFCell cell = row.getCell(j);
                cell.setCellStyle(cellStyle);
            }
        }*/

        /*HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);//字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font.setFontName("宋体");*/



        //cellStyle.setFont(font);
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        //cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
    }


    private Map<String, Object> getMap(Map<String, Object> details, String key) {
        Object o = details.get(key);
        if (o != null && o instanceof Map) {
            return (Map<String, Object>)o;
        }
        return null;
    }
}
