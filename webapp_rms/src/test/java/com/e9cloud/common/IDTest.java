package com.e9cloud.common;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.support.DisableException;
import com.e9cloud.core.support.FreezeException;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/2/15.
 */
public class IDTest{

    @Test
    public void testRandomUUID(){

        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            System.out.println(temp);
            if("1".equals(temp)){
                a.remove(temp);
            }
        }

        System.out.println(JSonUtils.toJSon(a));

        //System.out.println(Long.parseLong("3D", 16));

        /*try {
            File mp3 = new File("F:\\KwDownload\\song\\CALL.mp4");

            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(mp3);
            System.out.println(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer[] i = {10};
        ArrayUtils.contains(i,10);

        int a = 10;
        int r = 3;
        for (int ii=0,jj=0; ii<10; ii++) {

            System.out.println(ii+":"+jj);
            if (ii%r == 2) {jj++;}
        }

        System.out.println(10%3==0?10/3:(10/3+1));*/
    }

    public static void main(String[] args) {
        //File file1 = new File("D:\\test\\dest\\sss.txt");
        //File file2 = new File("D:\\test\\targ\\sss.txt");
        //file1.renameTo(file2);

       /* try {
            FileUtils.moveFile(file2, file1);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println(RegexUtils.chechAnyChar("9sseeeessee99888",1,10));


        System.out.println(Tools.isNotNullStr(""));
        System.out.println("".trim());

        String ss_ = "-aaa-aaa---aaa";
        System.out.println(ss_.split("-").length);
        System.out.println(Arrays.toString(ss_.split("-")));

        RelayGroup relayGroup = new RelayGroup2();

        RelayGroup2 rg = relayGroup.converto();

        System.out.println(rg.getG());

        String remoteFilePath = "http://lib.h-ui.net/Hui-iconfont/1.0.8/demo.html";
        String localFilePath = "D:\\downloads\\1.0.8\\demo.html";

        downloadFile(remoteFilePath, localFilePath);
    }

    public static void downloadFile(String remoteFilePath, String localFilePath)
    {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void httpGet() {
        HttpUtil.get("http://www.baidu.com");
    }

    @Test
    public void testOStream () {
        // 得到待执行任务列表
        List<SpRegTask> taskList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            SpRegTask task = new SpRegTask();
            task.setAddtime(new Date(System.currentTimeMillis() + i*10000));
            task.setSipphone((i+1)%3 + "");
            taskList.add(task);
        }

        // 按照sip号码进行分组
        Map<String, List<SpRegTask>> tasks = taskList.stream().collect(Collectors.groupingBy(SpRegTask::getSipphone));

        tasks.forEach((sipphone, task)->{
            System.out.println(sipphone);
            task.forEach((t)->{
                System.out.println(t.getAddtime());
            });
        });
    }

    @Test
    public void testAA() {
        StringBuilder dispatchName = new StringBuilder();
        String dispatchNames = dispatchName.length() > 0 ? dispatchName.substring(0, dispatchName.length()-1) : "";
        //dispatchName.substring(0,dispatchName.length()-1);

    }

}
