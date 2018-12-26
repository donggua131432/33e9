package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.util.ZipUtils;
import com.e9cloud.mybatis.domain.Account;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
    public List<StafMonthFile> getStafMonthFileList(HttpServletRequest request){
        logger.info("------------------------------------------------GET StafMonthFileController getStafMonthFileList START----------------------------------------------------------------");
        //获取当前用户信息、
        Account account = this.getCurrUser(request);
        Map<String, String> map = new HashMap<String, String>();
        map.put("stype", request.getParameter("stype"));
        map.put("feeid", account.getFeeid());
        List<StafMonthFile> list = stafMonthFileService.getStafMonthFileList(map);

        return list;
    }


    @RequestMapping("/monthFileDownload")
    @ResponseBody
    public ResponseEntity<byte[]> monthFileDownload(HttpServletRequest request) {
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filePath = appConfig.getMonthFilePath()+request.getParameter("file");
        //获取文件名
        String[] strArray = filePath.replaceAll("\\\\","/").split("/");
        headers.setContentDispositionFormData("attachment", strArray[strArray.length-1]);
        File file = new File(filePath);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.info("--------------------------------------读取文件失败："+e);
            //e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/dayFileDownload")
    @ResponseBody
    public ResponseEntity<byte[]> dayFileDownload(HttpServletRequest request) {
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filePath = appConfig.getDayFilePath()+request.getParameter("file");
        //获取文件名
        String[] strArray = filePath.replaceAll("\\\\","/").split("/");
        headers.setContentDispositionFormData("attachment", strArray[strArray.length-1]);
        File file = new File(filePath);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.info("--------------------------------------读取文件失败："+e);
            //e.printStackTrace();
        }
        return null;
    }

    /*@RequestMapping("/dayAllFileDownload")
    public ResponseEntity<byte[]> dayAllFileDownload(HttpServletRequest request) {
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filePath = appConfig.getDayFilePath()+request.getParameter("file");
        //获取文件名
        String[] strArray = filePath.replaceAll("\\\\","/").split("/");

        headers.setContentDispositionFormData("attachment", strArray[strArray.length-1]);
        File file = new File(filePath);
        String[] filelist = file.list();
        try {
            for (int i = 0; i < filelist.length; i++) {
                File subFile = new File(filePath + "/" + filelist[i]);
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(subFile), headers, HttpStatus.OK);
            }
        } catch (IOException e) {
            logger.info("--------------------------------------读取文件失败："+e);
            //e.printStackTrace();
        }
        return null;
    }*/

    @RequestMapping("/dayAllFileDownload")
    public void downLoadZipFile(HttpServletResponse response, String file) throws IOException{
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        String filePath = appConfig.getDayFilePath() + file;

        String zipName = file.substring(file.lastIndexOf("/") + 1) + ".zip";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        File fileDir = new File(filePath);

        try {
            for(File f : fileDir.listFiles()){
                ZipUtils.zipFile(filePath + "/" + f.getName(), out);
                response.flushBuffer();
            }
        } catch (IOException e) {
            logger.info("--------------------------------------读取文件失败："+e);
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    @RequestMapping("/checkFilePath")
    @ResponseBody
    public String[] checkFilePath(HttpServletRequest request) {
        logger.info("------------------------------------------------GET StafMonthFileController monthFileDownload START----------------------------------------------------------------");
        String filePath = appConfig.getDayFilePath() + request.getParameter("file");

        logger.info("----------------the filePath is : {}---------------", filePath);

        //获取文件名
        String[] strArray = filePath.replaceAll("\\\\","/").split("/");
        File file = new File(filePath);
        String[] names = new String[1];
        try {
            if (file.exists()) {
                names = file.list();
            }
        } catch (Exception e) {
            logger.error("--------------------------------------检查文件失败：" + e);
        }
        return names;
    }

}
