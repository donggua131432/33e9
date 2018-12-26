package com.e9cloud.core.util;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.BaseLogger;
import com.sun.media.jfxmediaimpl.MediaUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

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
public class FileUtil implements BaseLogger {

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

    private static String getNasVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getNasVoicePath();
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

    /**
     * 递归创建文件目录及文件
     * @param file
     */
    public static void mkParentDir(File file){
        mkDir(file.getParentFile());
    }

    // 判断图片的后缀名是否符合要求
    public static long getFileSize(MultipartFile file) {

        logger.info("====================== checkFileSize start=========================");

        if (file == null) {
            throw new RuntimeException("----------------this file is null----------------");
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

    public static void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = getNasVoicePath() + path;
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

    // 文件大小(扩展方法)
    public static long getFileSizebak(MultipartFile file) {

        logger.info("====================== checkFileSize start=========================");

        if (file == null) {
            return  0;
        }

        logger.info("====================== checkFileSize end=========================");

        return file.getSize();
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

}
