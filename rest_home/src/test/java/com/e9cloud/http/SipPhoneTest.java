package com.e9cloud.http;

/**
 * Created by dukai on 2016/10/27.
 */
public class SipPhoneTest {


    public static void main(String[] args){
        //String regex_sip_phone = "^[0][0-9]{2,3}[7][7][0-9]{7,8}$";
        //String str = "0776768132499";
        //System.out.println(str.matches(regex_sip_phone));



        //sipPhoneCallbackTest("json",true);

        //setCallSwitchTest("json",true);
        //queryCallSwitchTest("xml", true);

        //setLongDistanceCallTest("json",true);
        //queryLongDistanceCallTest("json", true);



        //String str = "600.0";
        //double doub = Double.parseDouble(str);
        //int it= (int) Double.parseDouble("600.0");
        //System.out.println((int) Double.parseDouble("600.0"));

        //System.out.println(System.currentTimeMillis());
    }

    /**
     * @param typeBody
     * @param localHostFlag 是否为本地请求 true是本地  false其他
     */
    public static void sipPhoneCallbackTest(String typeBody, boolean localHostFlag){
        System.out.println("sipPhoneCallbackTest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="50188815a6e14f57a2659668623851aa";
            authToken="bbe4be0f7c0247148a90dedb986ca4c3";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/SipPhone/callback";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"from\":\"075577123456\"," +
                        "\"to\":\"13476937349\"," +
                        "\"maxCallTime\":600," +
                        "\"userData\":\"DK\"" +
                        "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"from\":\"100003\"," +
                        "\"to\":\"0\"," +
                        "\"maxCallTime\":\"0\"," +
                        "\"userData\":\"DK\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<from>07557712345678</from>" +
                        "<to>07557787654321</to>" +
                        "<maxCallTime>600</maxCallTime>" +
                        "<userData>DK</userData>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<from>100003</from>" +
                        "<to>1</to>" +
                        "<maxCallTime>0</maxCallTime>" +
                        "<userData>2016/10/09 10:25:00</userData>" +
                        "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }


    public static void setCallSwitchTest(String typeBody, boolean localHostFlag){
        System.out.println("setCallSwitchTest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="50188815a6e14f57a2659668623851aa";
            authToken="bbe4be0f7c0247148a90dedb986ca4c3";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/SipPhone/setCallSwitch";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"," +
                        "\"callSwitchFlag\":\"01\"" +
                        "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"," +
                        "\"callSwitchFlag\":\"01\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "<callSwitchFlag>00</callSwitchFlag>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "<callSwitchFlag>00</callSwitchFlag>" +
                        "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }

    public static void queryCallSwitchTest(String typeBody, boolean localHostFlag){
        System.out.println("queryCallSwitchTest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="50188815a6e14f57a2659668623851aa";
            authToken="bbe4be0f7c0247148a90dedb986ca4c3";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/SipPhone/queryCallSwitch";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"" +
                        "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>075577123456</sipPhoneNumber>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }


    public static void setLongDistanceCallTest(String typeBody, boolean localHostFlag){
        System.out.println("setLongDistanceCallTest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="9634ae3afc8e4746a8adcdeb60683416";
            authToken="4a2f2eba55bb42baa3b17459643678d0";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/SipPhone/setLongDistanceCall";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"," +
                        "\"longDistanceFlag\":00" +
                        "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"7433e9219b1642b08b3f5b23c12c44e5\"," +
                        "\"sipPhoneNumber\":\"076977563214\"," +
                        "\"longDistanceFlag\":\"01\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "<longDistanceFlag>00</longDistanceFlag>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "<longDistanceFlag>00</longDistanceFlag>" +
                        "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }


    public static void queryLongDistanceCallTest(String typeBody, boolean localHostFlag){
        System.out.println("queryLongDistanceCallTest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="50188815a6e14f57a2659668623851aa";
            authToken="bbe4be0f7c0247148a90dedb986ca4c3";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/SipPhone/queryLongDistanceCall";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"" +
                        "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"cfeb83014a3411e6958f000c29779dd9\"," +
                        "\"sipPhoneNumber\":\"075577123456\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>cfeb83014a3411e6958f000c29779dd9</appId>" +
                        "<sipPhoneNumber>07557712345678</sipPhoneNumber>" +
                        "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken, url, typeBody, body);
    }
}
