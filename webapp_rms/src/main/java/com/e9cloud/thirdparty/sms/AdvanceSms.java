package com.e9cloud.thirdparty.sms;

import com.e9cloud.core.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 */
public class AdvanceSms {
    // public static final String DEMO_URL = "http://localhost:7001/GsmsHttp";//IP和端口号需要更改为正式环境下的。

    public static String send(String address, Map<String, Object> params, String charterset) {

        String buf = "";

        try {

           /* HttpUtil.get(address);

            URL url = new URL(address);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            String sendSmsData = organizationData(params, charterset);
            out.write(sendSmsData);
            out.flush();
            out.close();*/

            buf = HttpUtil.get(address + "?" + organizationData(params, charterset));

            /*BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";

            while ( (line = br.readLine()) != null ) {
                buf.append(line);
            }

            System.out.println(buf.toString());*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buf;
    }

    public static String organizationData(Map<String, Object> params, String charterset){

        StringBuilder sendBuilder = new StringBuilder();

        //sendBuilder.append("username=51415:admin");//机构ID:用户登录名
        sendBuilder.append("username").append("=").append(params.get("username"));

        //sendBuilder.append("&password=1");//密码
        sendBuilder.append("&").append("password").append("=").append(params.get("password"));

        //sendBuilder.append("&from=13049881352");//发送手机号码
        sendBuilder.append("&").append("from").append("=").append(params.get("from"));

        //sendBuilder.append("&to=13049881352,13049881353");//接收手机号，多个号码间以逗号分隔且最大不超过100个号码
        sendBuilder.append("&").append("to").append("=").append(params.get("to"));

        //sendBuilder.append("&expandPrefix=102");//自扩展端口(可选,如果没有传入空即可)
        sendBuilder.append("&").append("expandPrefix").append("=").append(params.get("expandPrefix"));

        //sendBuilder.append("&content=短信测试内容");//发送内容,标准内容不能超过70个汉字/GBK
        String content = params.get("content").toString();
        try {
            content = URLEncoder.encode(content, charterset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendBuilder.append("&").append("content").append("=").append(content);

        //sendBuilder.append("&presendTime=2015-08-05 09:32:00");//发送时间
        sendBuilder.append("&").append("presendTime").append("=").append(params.get("presendTime"));

        //sendBuilder.append("&isVoice=0|0|0|0");//是否语音,参见文档
        sendBuilder.append("&").append("isVoice").append("=").append("false");
        /**
         * 是否语音短信,若为空默认为普通短信.该参数格式:是否语音(0表示普通短信,1表示语音短信)|重听次数|重拨次数|是否回复(0表示不回复,1表示回复)
         * 如:isVoice=”1|1|2|0” 即:语音短信,重听次数1,重拨次数2,不回复.
         */

        System.out.println(sendBuilder.toString());

        return sendBuilder.toString();
    }
}
