package com.e9cloud.demo.cb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class CBTcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {


        if (msg != null && (msg instanceof  CBMsg))
        {
            CBMsg cbMsg = (CBMsg)msg;

            System.out.println(ctx.channel().remoteAddress()+"->Server :"+ cbMsg.toString());

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