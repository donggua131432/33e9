package com.e9cloud.common;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.Encodes;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.impl.AccountServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class CommonTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext.xml");

        AccountService accountService = context.getBean(AccountService.class);

        accountService.checkEmailUnique("");

    }

    @Test
    public void testEncodeHex() {
        String email = "zhongjun0218@126.com";

        String eStr = Encodes.encodeHex(email.getBytes());
        System.out.println(eStr);

        System.out.println(new String(Encodes.decodeHex(eStr)));
    }

    @Test
    public void testEx() {
        List<String> ss = new ArrayList<>();
        ss.get(0);
    }

    @Test
    public void testID() {
        for (int i = 0; i < 10; i++) {
            System.out.println(ID.randomUUID());
        }
    }

    @Test
    public void testCopyFile() throws IOException {
        File srcFile = new File("D:/curl.exe");
        File destFile = new File("D:/uur.exe");
        FileUtils.copyFile(srcFile, destFile);
    }

    @Test
    public void testString() {
        String i = new String("1");
        System.out.println(i.equals("1"));
        System.out.println(testStr(i));

        File file = new File("F:\\发版\\sql\\20161112");
        String[] names = file.list();
        System.out.println(Arrays.toString(names));
    }

    private boolean testStr(String s){
        return s.equals("1");
    }

}
