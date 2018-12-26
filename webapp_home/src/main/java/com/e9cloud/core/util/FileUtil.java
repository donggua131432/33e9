package com.e9cloud.core.util;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.BaseLogger;
import com.sun.media.jfxmediaimpl.MediaUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于文件传输
 * 格式校验等
 * Created by wzj on 2016/3/9.
 */
public class FileUtil extends BaseLogger{

    /**
     * 将文件上传默认路径上去
     * @param file 文件
     * @param fileName 文件名
     * @return
     */
    public static boolean uploadToDefaultPath(MultipartFile file, String fileName) {
        return uploadToDefaultPath(file, fileName, "");
    }

    /**
     * 将文件上传默认路径上去
     * @param file 文件
     * @param fileName 文件名
     * @param path 中间路径
     * @return
     */
    public static boolean uploadToDefaultPath(MultipartFile file, String fileName, String path) {

        boolean success = false;

        //取得上传文件
        if(file != null){
            try{
                String nasPath = getNasPath();

                //定义上传路径
                String p = nasPath + path;

                File localDir = new File(p);
                if (!localDir.exists()) { // 如果不存在则创建
                    mkDir(localDir);
                }

                String f = p + "/" + fileName;

                logger.info("-----------uplaod url {} and nasPath {}---------", f, nasPath);

                file.transferTo(new File(f));

                success = true;

            }catch (IOException e){
                e.printStackTrace();
                success = false;
            }
        }

        logger.info("-----------file is null---------");

        return success;
    }

    /**
     * 将文件上传默认路径上去
     * @param file 文件
     * @param fileName 文件名
     * @param path 中间路径
     * @return
     */
    public static boolean uploadTempVoiceToPath(MultipartFile file, String fileName, String path) {

        boolean success = false;

        //取得上传文件
        if(file != null){
            try{
                String nasPath = getTempVoicePath();

                //定义上传路径
                String p = nasPath + path;

                File localDir = new File(p);
                if (!localDir.exists()) { // 如果不存在则创建
                    mkDir(localDir);
                }

                String f = p + "/" + fileName;

                logger.info("-----------uplaod url {} and nasPath {}---------", f, nasPath);

                file.transferTo(new File(f));

                success = true;

            }catch (IOException e){
                e.printStackTrace();
                success = false;
            }
        }

        logger.info("-----------file is null---------");

        return success;
    }

    /**
     * 将语音验证码文件上传默认路径上去
     * @param file 文件
     * @param fileName 文件名
     * @param path 中间路径
     * @return
     */
    public static boolean uploadVoiceverifyTempToPath(MultipartFile file, String fileName, String path) {

        boolean success = false;

        //取得上传文件
        if(file != null){
            try{
                String nasPath = getVoiceverifyTempPath();

                //定义上传路径
                String p = nasPath + path;

                File localDir = new File(p);
                if (!localDir.exists()) { // 如果不存在则创建
                    mkDir(localDir);
                }

                String f = p + "/" + fileName;

                logger.info("-----------uplaod url {} and nasPath {}---------", f, nasPath);

                file.transferTo(new File(f));

                success = true;

            }catch (IOException e){
                e.printStackTrace();
                success = false;
            }
        }

        logger.info("-----------file is null---------");

        return success;
    }

    /**
     * 将文件上传默认路径上去
     * @param file 文件
     * @param fileName 文件名
     * @param path 中间路径
     * @return
     */
    public static boolean uploadToVoicePath(MultipartFile file, String fileName, String path) {

        boolean success = false;

        //取得上传文件
        if(file != null){
            try{
                String nasPath = getLocalVoicePath();

                //定义上传路径
                String p = nasPath + path;

                File localDir = new File(p);
                if (!localDir.exists()) { // 如果不存在则创建
                    mkDir(localDir);
                }

                String f = p + "/" + fileName;

                logger.info("-----------uplaod url {} and nasPath {}---------", f, nasPath);

                file.transferTo(new File(f));

                success = true;

            }catch (IOException e){
                e.printStackTrace();
                success = false;
            }
        }

        logger.info("-----------file is null---------");

        return success;
    }

    /**
     * 图片显示
     * @param path
     * @param file
     * @return
     */
    public static String readFileToString(String path, String file){

        String p = getNasPath() + path + "/" + file;
        File f = new File(p);

        logger.info("-----------file url is {}---------", p);

        if (f.exists()) {
            try {
                return Encodes.encodeBase64(FileUtils.readFileToByteArray(f));
            } catch (IOException e) {
                logger.info("-----------file error---------");
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 图片显示
     * @param path
     * @param file
     * @return
     */
    public static BufferedInputStream readFileToStream(String path, String file){

        String p = getNasPath() + path + "/" + file;
        File f = new File(p);

        logger.info("-----------file url is {}---------", p);

        if (f.exists()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                return bis;
            } catch (IOException e) {
                logger.info("-----------file error---------");
                e.printStackTrace();
            }
        }

        return null;
    }

    private static String getNasPath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getNasPath();
    }

    /**
     * 递归创建文件目录及文件
     * @param file
     */
    public static void getFile(String path, String file){
        File dir = new File(path);
        if (dir.exists()) {
            String[] names = dir.list();


        }
    }

    /**
     * 递归创建文件目录及文件
     * @param file
     */
    public static void mkDir(File file){
        if(file.getParentFile().exists()){
            file.mkdir();
        }else{
            mkDir(file.getParentFile());
            file.mkdir();
        }
    }

    // 文件大小
    public static long getFileSize(MultipartFile file) {

        logger.info("====================== checkFileSize start=========================");

        if (file == null) {
            throw new RuntimeException("----------------this file is null----------------");
        }

        logger.info("====================== checkFileSize end=========================");

        return file.getSize();
    }

    // 文件大小(扩展方法)
    public static long getFileSizebak(MultipartFile file) {

        logger.info("====================== checkFileSize start=========================");

        if (file == null) {
            return  0;
        }

        logger.info("====================== checkFileSize end=========================");

        return file.getSize();
    }

    // 判断图片的后缀名是否符合要求
    public static boolean checkExtensionName(MultipartFile file, String suf) {
        String extensionName = getFormatName(file);
        logger.info("====================== img format name is " + extensionName + " =========================");
        if (extensionName == null) {
            return false;
        }
        Pattern p = Pattern.compile(suf);
        Matcher m = p.matcher(extensionName.toLowerCase());
        return m.matches();
    }

    // 获取图片的格式
    private static String getFormatName(MultipartFile obj) {
        logger.info("====================== parse img format name start =========================");

        ImageInputStream iis = null;
        try {
            // Create an image input stream on the image
            iis = ImageIO.createImageInputStream(obj.getInputStream());

            // Find all image readers that recognize the image format
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                // No readers found
                return null;
            }

            // Use the first reader
            ImageReader reader = iter.next();

            // Close stream
            // iis.close();
            logger.info("====================== parse img format end=========================");
            // Return the format name
            String formatName = reader.getFormatName();

            logger.info("====================== parse img format name is : {}=========================", formatName);

            return formatName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // The image could not be read
        return null;
    }

    // 文件是否是wav格式
    public static boolean isWavFile(MultipartFile obj) {
        logger.info("====================== parse file format name start =========================");

        InputStream is = null;

        try {
            is = obj.getInputStream();

            byte[] b = new byte[100];
            is.read(b, 0, 100);

            String contentType = MediaUtils.fileSignatureToContentType(b, 100);

            logger.info("====================== parse file format name is : {}=========================", contentType);

            return MediaUtils.CONTENT_TYPE_WAV.equals(contentType);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("====================== parse img format end=========================");

        return false;
    }

    // 文件是否是wav格式
    public static boolean isWavFile(InputStream is) {
        logger.info("====================== parse file format name start =========================");

        if (is == null){
            return false;
        }

        try {

            byte[] b = new byte[100];
            is.read(b, 0, 100);

            String contentType = MediaUtils.fileSignatureToContentType(b, 100);

            logger.info("====================== parse file format name is : {}=========================", contentType);

            return MediaUtils.CONTENT_TYPE_WAV.equals(contentType);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("====================== parse img format end=========================");

        return false;
    }

    // 文件是否是MP3格式
    public static boolean isMP3File(MultipartFile obj) {
        logger.info("====================== parse file format name start =========================");

        InputStream is = null;

        try {
            is = obj.getInputStream();

            byte[] b = new byte[100];
            is.read(b, 0, 100);

            String contentType = MediaUtils.fileSignatureToContentType(b, 100);

            logger.info("====================== parse file format name is : {}=========================", contentType);

            return MediaUtils.CONTENT_TYPE_MPA.equals(contentType);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("====================== parse img format end=========================");

        return false;
    }

    // 文件是否是map3格式
    public static boolean isMP3File(InputStream is) {
        logger.info("====================== parse file format name start =========================");

        if (is == null){
            return false;
        }

        try {

            byte[] b = new byte[100];
            is.read(b, 0, 100);

            String contentType = MediaUtils.fileSignatureToContentType(b, 100);

            logger.info("====================== parse file format name is : {}=========================", contentType);

            return MediaUtils.CONTENT_TYPE_MPA.equals(contentType);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("====================== parse img format end=========================");

        return false;
    }

    private static String getTempVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getTempVoicePath();
    }

    private static String getVoiceverifyTempPath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getVoiceverifyTempPath();
    }

    private static String getNasTempVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getTempVoicePathNas();
    }

    private static String getNasVoiceverifyTempPath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getNasVoiceverifyTempPath();
    }

    private static String getNasVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getVoicePath();
    }

    private static String getLocalVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getLocalVoicePath();
    }

    private static String getIvrEccPathNas(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getIvrEccPathNas();
    }

    // 下载wav格式音频

    public static void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = getLocalVoicePath() + path;
            File file = new File(newPath);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 下载wav格式音频   downloadIvr

    public static void downloadIvr(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = getIvrEccPathNas() + path;
            File file = new File(newPath);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // 下载wav格式音频-----指定路径为：语音模板

    public static void downloadTempVoice(String path, HttpServletResponse response,String vType) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = getTempVoicePath() + path;
            if("01".equals(vType)){
                newPath = getNasTempVoicePath() + path;
            }
            File file = new File(newPath);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 下载语音验证码wav格式音频-----指定路径为：语音模板

    public static void downloadVoiceverifyTemp(String path, HttpServletResponse response,String vType) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = getVoiceverifyTempPath() + path;
            if("01".equals(vType)){
                newPath = getNasVoiceverifyTempPath() + path;
            }
            File file = new File(newPath);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 删除文件夹的音频文件(根据appID的唯一性delete)

    public static boolean deleteFile(String sPath) {
        String newPath = getNasVoicePath() + sPath +".wav";
        boolean flag = false;
        File file = new File(newPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            logger.info("the file is existed and delete it");
            file.delete();
            flag = true;
        }
        return flag;
    }

    // 删除隐私号相关的音频文件(根据appID的唯一性delete)

    public static boolean deleteVoice(String sPath) {
        String newPath = getNasVoicePath() + sPath +"_callin_failed.wav";
        boolean flag = false;
        File file = new File(newPath);
        logger.info("\"the mask voice path  is {}\", newPath");
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            logger.info("the mask file is existed and delete it");
            file.delete();
            flag = true;
        }
        return flag;
    }



    // 删除语音模板相关的音频文件(根据appID的唯一性delete)
    public static boolean deleteTempVoice(String appid,String vPath) {

        //删除时，在默认路径下，每个不同的appid一个对应文件夹
        String newPath = getNasTempVoicePath() + vPath;
        logger.info("the mask voice path is {}", newPath);
        boolean flag = false;
        File file = new File(newPath);

        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            logger.info("the mask file is existed and delete it");
            file.delete();
            flag = true;
        }
        return flag;
    }

    // 删除语音模板相关的音频文件(根据appID的唯一性delete)
    public static boolean deleteVoiceverifyTemp(String appid,String vPath) {

        //删除时，在默认路径下，每个不同的appid一个对应文件夹
        String newPath = getNasVoiceverifyTempPath() + vPath;
        logger.info("the voiceverifytemp  path is {}", newPath);
        boolean flag = false;
        File file = new File(newPath);

        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            logger.info("the voiceverifytemp file is existed and delete it");
            file.delete();
            flag = true;
        }
        return flag;
    }
}
