package com.e9cloud.rest.cb;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MsgEncoder extends MessageToByteEncoder<CBMsg>
{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CBMsg cbMsg, ByteBuf byteBuf) throws Exception
    {
        if(cbMsg != null)
        {
            ByteBuf bf =  cbMsg.msgToProtocol();
            byteBuf.writeBytes(bf);
        }
    }
}
