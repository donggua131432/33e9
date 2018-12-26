package com.e9cloud.http;

/**
 * Created by dukai on 2016/9/27.
 */
public class VoiceNotifyTest{

    public static void main(String[] args){
        voiceNotifyRequest("json",true);
    }

    /**
     * @param typeBody
     * @param localHostFlag 是否为本地请求 true是本地  false其他
     */
    public static void voiceNotifyRequest(String typeBody, boolean localHostFlag){
        System.out.println("voiceNotifyRequest");
        String accountSid;
        String authToken;
        if(localHostFlag){
            accountSid="d964b1fc0a8d4f7ebf7b72481ca8376c";
            authToken="1e98b8e96c3b4971ac07b17f9ed567ac";
        }else{
            accountSid="50188815a6e14f57a2659668623851aa";
            authToken="bbe4be0f7c0247148a90dedb986ca4c3";
        }
        String url=SendClient.serverUri + "/2016-01-01/Accounts/"+accountSid+"/VoiceNotify/request";
        String body;
        if("json".equals(typeBody)){
            if(localHostFlag){
                body = "{\"req\":{" +
                        "\"appId\":\"7290fa01293c483fb2709c690f213272\"," +
                        "\"voiceRecId\":\"100003\"," +
                        "\"dtmfFlag\":\"0\"," +
                        "\"orderFlag\":\"0\"," +
                        "\"orderTime\":\"2016/10/11 17:40:00\"," +
                        "\"moduleParams\":\"dukai\"," +
                        "\"toList\":[\"13410240093\",\"18617069178\",\"18617069178\"]," +
                        "\"displayNum\":\"4008006013\"" +
                    "}}";
            }else{
                body = "{\"req\":{" +
                        "\"appId\":\"57bd277e2d9a45c1a9c18bdd211626bb\"," +
                        "\"voiceRecId\":\"100040\"," +
                        "\"dtmfFlag\":\"0\"," +
                        "\"orderFlag\":\"0\"," +
                        "\"orderTime\":\"2016/10/13 17:40:00\"," +
                        "\"moduleParams\":\"dukai\"," +
                        "\"toList\":[\"18617069178\",\"18617069178\"]," +
                        "\"displayNum\":\"\"" +
                        "}}";
            }
        }else{
            if(localHostFlag){
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                        "<req>" +
                        "<appId>7290fa01293c483fb2709c690f213272</appId>" +
                        "<voiceRecId>100003</voiceRecId>" +
                        "<dtmfFlag>1</dtmfFlag>" +
                        "<orderFlag>0</orderFlag>" +
                        "<orderTime>2016/10/09 10:25:00</orderTime>" +
                        "<moduleParams>dukai</moduleParams>" +
                        "<toList>[13410240093,18617069178,18617069178,18617069178]</toList>" +
                        "<displayNum>18312345678</displayNum>" +
                        "</req>";
            }else{
                body = "<?xml version='1.0' encoding='utf-8'?>" +
                   "<req>" +
                        "<appId>57bd277e2d9a45c1a9c18bdd211626bb</appId>" +
                        "<voiceRecId>100040</voiceRecId>" +
                        "<dtmfFlag>1</dtmfFlag>" +
                        "<orderFlag>1</orderFlag>" +
                        "<orderTime>2016/10/09 10:25:00</orderTime>" +
                        "<moduleParams>dukai</moduleParams>" +
                        "<toList>[10011,18617069178]</toList>" +
                        "<displayNum></displayNum>" +
                   "</req>";
            }
        }
        SendClient.sendHttpAuth("post", accountSid, authToken,url, typeBody, body);
    }
}
