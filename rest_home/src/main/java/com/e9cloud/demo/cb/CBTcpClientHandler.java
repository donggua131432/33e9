package com.e9cloud.demo.cb;



import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class CBTcpClientHandler extends ChannelInboundHandlerAdapter
{

      @Override
      public void channelActive(ChannelHandlerContext ctx)
      {
          //ctx.writeAndFlush(firstMessage);
          System.out.println("channelActive");
      }
  
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg)
      {
          System.out.println("---msg---" + msg.toString());
          if (msg != null && (msg instanceof  CBMsg))
          {
              CBMsg cbMsg = (CBMsg)msg;
              String remoteAddress = ctx.channel().remoteAddress().toString();
              System.out.println(ctx.channel().remoteAddress()+"->clent :"+ cbMsg.toString());

          }
          //ctx.write(msg);
      }
  
     @Override
      public void channelReadComplete(ChannelHandlerContext ctx)
      {
         ctx.flush();
      }
  
     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
     {         // Close the connection when an exception is raised.
         cause.printStackTrace();
         ctx.close();
      }
  }