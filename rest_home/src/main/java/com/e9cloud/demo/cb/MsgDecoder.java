package com.e9cloud.demo.cb;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class MsgDecoder  extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception
    {
        if (byteBuf == null || !byteBuf.isReadable())
        {
            return;
        }

        CBMsg msg = CBMsg.protocolToMsg(byteBuf);

        list.add(msg);

    }
}
