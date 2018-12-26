package com.e9cloud.pcweb.record.action;

import com.e9cloud.mybatis.domain.StafMonthFile;
import com.e9cloud.mybatis.service.StafMonthFileService;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/12.
 */
@Controller
@RequestMapping("/monthFile")
public class StafMonthFileController extends BaseController {

    @Autowired
    private StafMonthFileService stafMonthFileService;

    @RequestMapping("/getStafMonthFileList")
    @ResponseBody
    public List<StafMonthFile> getStafMonthFileList(StafMonthFile stafMonthFile){
        logger.info("------------------------------------------------GET StafMonthFileController getStafMonthFileList START----------------------------------------------------------------");
        //获取当前用户信息、
        Map<String, String> map = new HashMap<String, String>();
        map.put("stype", stafMonthFile.getStype());
        map.put("feeid", stafMonthFile.getFeeid());

        return stafMonthFileService.getStafMonthFileList(map);
    }


    @RequestMapping("/monthFileDownload")
    @ResponseBody
    public ResponseEntity<byte[]> monthFileDownload(HttpServletRequest request) {
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filePath = appConfig.getMonthFilePath() + request.getParameter("file");
        //获取文件名
        String[] strArray = filePath.replaceAll("\\\\","/").split("/");
        headers.setContentDispositionFormData("attachment", strArray[strArray.length-1]);
        File file = new File(filePath);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.info("--------------------------------------读取文件失败：" + e);
            //e.printStackTrace();
        }
        return null;
    }

}
