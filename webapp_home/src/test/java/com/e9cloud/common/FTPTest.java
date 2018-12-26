package com.e9cloud.common;

import com.e9cloud.core.util.FtpFileUtil;
import com.e9cloud.core.util.JSonUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class FTPTest {

    @Test
    public void testUpLoadFromDisk(){
        try {
            FileInputStream in = new FileInputStream(new File("D:/curl.txt"));
            boolean flag = FtpFileUtil.uploadFile("10.9.0.81", 21, "root", "33e9.com", "/web/logs", "test.txt", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson() {
        Map<String,String> o = new HashMap<>();
        //id, log_name, log_content, log_type, user_id, username, nick, ip, role_name, create_time
        o.put("log_name", "llll");
        System.out.println(JSonUtils.toJSon(o));
    }
}
