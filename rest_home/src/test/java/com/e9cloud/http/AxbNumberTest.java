package com.e9cloud.http;

/**
 * @描述: 虚拟小号接口测试
 * @作者: DuKai
 * @创建时间: 2017/4/19 15:20
 * @版本: 1.0
 */
public class AxbNumberTest {

    //String static accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
    //String static authToken="1e98b8e96c3b4971ac07b17f9ed567ac";

    private static String accountSid="e600d6dff3d046aca1a6c0a6a0a9554f";
    private static String authToken="a5c1fbbdb4bb43ca9eb37b749dfd4065";


    public static void main(String[] args) {

        String voiceFile= "voice1db58610be56447bb254db53a25d979e.wav";

        System.out.println(voiceFile.split("\\.")[0]);
        //axbNumberBindTest("json");
        //axbNumberUnbindTest("json");
        //axbNumberHangupTest();
    }

    public static void axbNumberBindTest(String typeBody){
        System.out.println("axbNumberBindTest");
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/AxbNumber/bind";
        String body;
        if("json".equals(typeBody)){
            /*body = "{\"req\":{" +
                    "\"appId\":\"7290fa01293c483fb2709c690f213272\"," +
                    "\"telA\":\"18617069178\"," +
                    "\"telB\":\"13410240093\"," +
                    "\"areacode\":\"0779\"," +
                    "\"expiration\":60," +
                    "\"userData\":\"AA,BB,CC\"" +
                    "}}";*/

            body = "{\"req\":{" +
                    "\"appId\":\"a028f465d65c412ab5f3e9fc3ecf8560\"," +
                    "\"telA\":\"18617069178\"," +
                    "\"telB\":\"13410240093\"," +
                    "\"areacode\":\"0755\"," +
                    "\"expiration\":600," +
                    "\"userData\":\"A\"" +
                    "}}";
        }else{
            body = "<?xml version='1.0' encoding='utf-8'?>" +
                    "<req>" +
                    "<appId>7290fa01293c483fb2709c690f213272</appId>" +
                    "<telA>18617069178</telA>" +
                    "<telB>13410240093</telB>" +
                    "<areacode>0779</areacode>" +
                    "<expiration>60</expiration>" +
                    "<userData>AA,BB,CC</userData>" +
                    "</req>";
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }


    public static void axbNumberUnbindTest(String typeBody){
        System.out.println("axbNumberUnbindTest");
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/AxbNumber/unbind";
        String body;
        if("json".equals(typeBody)){
            /*body = "{\"req\":{" +
                    "\"appId\":\"7290fa01293c483fb2709c690f213272\"," +
                    "\"subid\":\"A2102X779X0008005970\"" +
                    "}}";*/

            body = "{\"req\":{" +
                    "\"appId\":\"2f3d3721b9924cdba942d62123aea8ee\"" +
                  /*  "\"subid\":\"A2102X755X0008430107\"" +*/
                    "}}";
        }else{
            body = "<?xml version='1.0' encoding='utf-8'?>" +
                    "<req>" +
                    "<appId>7290fa01293c483fb2709c690f213272</appId>" +
                    "<subid>A2102X779X0008005970</subid>" +
                    "</req>";
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }


    public static void axbNumberHangupTest(){
        System.out.println("axbNumberHangupTest");
        String url=SendClient.serverUri + "/axbNotify/hangup";
        String body = "{" +
                "\"calltype\":\"10\"," +
                "\"appid\":\"7290fa01293c483fb2709c690f213272\"," +
                "\"telA\":\"18612345678\"," +
                "\"telB\":\"1867654321\"," +
                "\"telX\":\"13012345678\"," +
                "\"calltime\":\"20170422163700\"," +
                "\"starttime\":\"20170422163710\"," +
                "\"releasetime\":\"20170422163810\"," +
                "\"callid\":\"9f1ffdf1273511e7a80d000c29779dd9\"," +
                "\"releasedir\":\"0\"," +
                "\"releasecause\":\"0\"," +
                "\"userData\":\"AA,BB,CC\"" +
                "}";
        SendClient.sendHttp("post", url, "json", body);
    }
}
