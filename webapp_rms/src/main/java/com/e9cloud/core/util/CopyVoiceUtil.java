package com.e9cloud.core.util;

import com.alibaba.druid.support.logging.Log;
import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.BaseLogger;

import java.io.*;

/**
 * Created by Administrator on 2016/3/29.
 */
public class CopyVoiceUtil implements BaseLogger {
    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param overlay      如果目标文件存在，是否覆盖
     * @param type         铃音类型common 普通的，mask 隐私号
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, boolean overlay, String appId, String type) {
        logger.info("源文件：" + srcFileName);
        String destFileName = "";
        if (Constants.VOICE_MASK.equals(type)) { // 号码失效提示音
            destFileName = getVoicePath() + appId + "_callin_failed.wav";
        } else {
            destFileName = getVoicePath() + appId + ".wav";
        }
        String srcVoicePath = getNasVoicePath();
        File srcFile = new File(srcVoicePath + srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            logger.info("源文件：" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            logger.info("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }
        logger.info("目标目录：" + destFileName);

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getVoicePath();
    }

    private static String getNasVoicePath(){
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        return appConfig.getNasVoicePath();
    }


}
