package com.e9cloud.http;

/**
 * @描述: 语音验证码测试
 * @作者: DuKai
 * @创建时间: 2017/5/3 15:19
 * @版本: 1.0
 */
public class VoiceValidateTest {

    //private static String accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
    //private static String authToken="1e98b8e96c3b4971ac07b17f9ed567ac";

    private static String accountSid="57efdc48e9064b7aa044b9e39c24ecae";
    private static String authToken="3444135ea81b41cab15103610b51c5b1";


    public static void main(String[] args) {
        voiceValidateTest("json");
    }

    public static void voiceValidateTest(String typeBody){
        System.out.println("voiceValidateTest");
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/VoiceValidate/requestCall";
        String body;
        if("json".equals(typeBody)){
            body = "{\"req\":{" +
                    "\"appId\":\"2f3d3721b9924cdba942d62123aea8ee\"," +
                    "\"voiceRecId\":\"100141\"," +
                    "\"verifyCode\":\"123456\"," +
                    "\"called\":\"15220273875\"," +
                    "\"callDisplayNumber\":\"\"" +
                    "}}";

            /*body = "{\"req\":{" +
                    "\"appId\":\"7290fa01293c483fb2709c690f213272\"," +
                    "\"voiceRecId\":\"123456\"," +
                    "\"verifyCode\":\"123456\"," +
                    "\"called\":\"1002\"," +
                    "\"callDisplayNumber\":\"\"" +
                    "}}";*/
        }else{
            body = "<?xml version='1.0' encoding='utf-8'?>" +
                    "<req>" +
                    "<appId>7290fa01293c483fb2709c690f213272</appId>" +
                    "<voiceRecId></voiceRecId>" +
                    "<verifyCode>123456</verifyCode>" +
                    "<called>18617069178</called>" +
                    "<callDisplayNumber></callDisplayNumber>" +
                    "</req>";
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }

}
