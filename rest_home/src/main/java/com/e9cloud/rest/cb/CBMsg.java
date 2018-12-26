package com.e9cloud.rest.cb;

import com.e9cloud.core.common.ID;
import com.e9cloud.util.ConstantsEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
@Component
public class CBMsg
{
    private List<TVObject>  header = new ArrayList<TVObject>();
    private String  body;

    public ByteBuf msgToProtocol()
    {
        int headerNum = header.size();

//        //心跳消息
//        if( headerNum == 0)
//        {
//            ByteBuf heartBeatBuf = Unpooled.buffer();
//            heartBeatBuf.writeInt(8);
//            heartBeatBuf.writeInt(4);
//            return  heartBeatBuf;
//        }

        int headerLen = 4;
        int bodyLen = 0;
        if(!StringUtil.isNullOrEmpty(body)){
            bodyLen = body.getBytes().length;
        }
        for (TVObject tv :header)
        {
            headerLen = headerLen + (tv.getValue().getBytes().length + 4 +4);
        }

        int totalLen = 4 + headerLen + bodyLen;
        ByteBuf msgBuffer = Unpooled.buffer(totalLen);

        msgBuffer.writeInt(totalLen);
        msgBuffer.writeInt(headerLen);
        boolean hertFlag = false;
        for (TVObject tv :header)
        {
            if(tv.getType()==4&&(ConstantsEnum.REST_HERT_CODE.getStrValue()).equals(tv.getValue())){
                hertFlag = true;
            }
            msgBuffer.writeBytes(TVObjectToByteBuffer(tv));
        }

        //心跳消息
        if(hertFlag&&bodyLen==0){
            return msgBuffer;
        }
        msgBuffer.writeBytes(body.getBytes());

        return msgBuffer;

    }

    public List<TVObject> getHeader() {
        return header;
    }

    public void setHeader(List<TVObject> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public  static   ByteBuf  TVObjectToByteBuffer(TVObject tv)
    {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(tv.getType());
        buf.writeInt(tv.getValue().getBytes().length);
        buf.writeBytes(tv.getValue().getBytes());

        return  buf;
    }

    public  static CBMsg protocolToMsg(ByteBuf byteBuf) throws  Exception
    {
        if (byteBuf == null || !byteBuf.isReadable())
        {
            return  null;
        }
        CBMsg msg = new CBMsg();


        int totalLen = byteBuf.readInt();
        int headerTotalLen = byteBuf.readInt();

//        //心跳消息
//        if(totalLen==8&&headerTotalLen ==4)
//        {
//            return  msg;
//        }

        int headerLen = headerTotalLen -4 ;
        int bodyLen = totalLen -4 - headerTotalLen;
        ByteBuf headerBuf =  byteBuf.readBytes(headerLen);
        boolean hertFlag = false;
        while (headerBuf.isReadable())
        {
            TVObject tv = new TVObject();

            int type = headerBuf.readInt();
            int length = headerBuf.readInt();
            ByteBuf valueBuf = headerBuf.readBytes(length);
            String value = new String(valueBuf.array(),"UTF-8") ;

            tv.setType(type);
            tv.setValue(value);
            msg.getHeader().add(tv);
            if(type==4&&(ConstantsEnum.REST_HERT_CODE.getStrValue()).equals(value)){
                hertFlag = true;
            }
        }
        if(hertFlag&&bodyLen==0){
            return msg;//心跳消息
        }
        ByteBuf bodyBuf =  byteBuf.readBytes(bodyLen);
        String body = new String(bodyBuf.array(),"UTF-8") ;
        msg.setBody(body);

        return  msg;
    }


    public static void main(String[] args) throws InterruptedException
    {

        CBMsg msg = new CBMsg();
        ByteBuf ht = msg.msgToProtocol();
        System.out.println(ht);


        TVObject tv1 = new TVObject();
        TVObject tv2 = new TVObject();
        String body ="00000008";
        tv1.setType(1);
        tv1.setValue("0004");

        tv2.setType(2);
        tv2.setValue("00005");

        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);

        msg.setHeader(header);
        msg.setBody(body);


        ByteBuf mgbf = msg.msgToProtocol();

        System.out.println(mgbf);


    }
    public  void getHbMsg(String version,String sncode)
    {

        TVObject tv1 = new TVObject();
        tv1.setType(1);
        tv1.setValue(version);
        TVObject tv2 = new TVObject();
        tv2.setType(2);
        tv2.setValue(String.valueOf(System.currentTimeMillis()));
        TVObject tv3 = new TVObject();
        tv3.setType(3);
        tv3.setValue(ID.randomUUID());
        TVObject tv4 = new TVObject();
        tv4.setType(4);
        tv4.setValue(ConstantsEnum.REST_HERT_CODE.getStrValue());
        TVObject tv5 = new TVObject();
        tv5.setType(5);
        tv5.setValue(sncode);
        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);
        header.add(tv3);
        header.add(tv4);
        header.add(tv5);
        this.setHeader(header);
    }
    @Override
    public String toString() {
        return "CBMsg{" +
                "header=" + header +
                ", body='" + body + '\'' +
                '}';
    }

}

