package com.e9cloud.rest.cb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class CBTcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {


        if (msg != null && (msg instanceof CBMsg))
        {
            CBMsg cbMsg = (CBMsg)msg;

            System.out.println(ctx.channel().remoteAddress()+"->Server :"+ cbMsg.toString());

//            //{'action':'CallInvite','type':1,'appId':'hhhhhhhhhhhhhhhhhhh','subId':'60000000000008mRrDm254585','caller':'13418477875','called':'18612345678','userFlag':1,'subType':1,'dateCreate':'2016-02-23 10:21:00','userData':'testData'}
//            //CB报文头4域值
//            String v1="0010";//版本号
//            String v2="1111111";//消息流水号
//            String v3="32323232323232323232323232323232";//32位标识号
//            String v4="0003";//消息类型
//            String body="{'action':'CallInvite','type':1,'appId':'hhhhhhhhhhhhhhhhhhh','subId':'60000000000008mRrDm254585','caller':'13418477875','called':'18612345678','userFlag':1,'subType':1,'dateCreate':'2016-02-23 10:21:00','userData':'testData'}";
//            ctx.writeAndFlush(CBTcpClient.hbMsg(v1,v2,v3,v4,body));
            ctx.writeAndFlush(msg);

        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}