package com.e9cloud.demo.cb;

import com.e9cloud.core.common.ID;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public final class CBTcpClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
//    static final String HOST = System.getProperty("host", "192.168.0.146");
//    static final int PORT = Integer.parseInt(System.getProperty("port", "7894"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                	 p.addLast("framerDecoder", new LengthFieldBasedFrameDecoder(64*1024, 0, 4, -4, 0));
                     p.addLast("msgDecoder", new MsgDecoder());

                     p.addLast("msgEncoder", new MsgEncoder());
                     p.addLast(new CBTcpClientHandler());
                 }
             });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();


            // f.channel().writeAndFlush(hbMsg().array());
           // f.channel().writeAndFlush(hbMsg());
            f.channel().writeAndFlush(ffMsg());

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public  static CBMsg hbMsg()
    {
        CBMsg msg = new CBMsg();

        TVObject tv1 = new TVObject();
        TVObject tv2 = new TVObject();
        //String body ="00000008";
        tv1.setType(1);
        tv1.setValue("0010");

        tv2.setType(2);
        tv2.setValue(String.valueOf(System.currentTimeMillis()));
        TVObject tv3 = new TVObject();
        tv3.setType(3);
        tv3.setValue(ID.randomUUID());
        TVObject tv4 = new TVObject();
        tv4.setType(4);
        tv4.setValue("0004");
        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);
        header.add(tv3);
        header.add(tv4);
        msg.setHeader(header);
        return msg;
    }

    public  static  CBMsg ffMsg()
    {
        CBMsg msg = new CBMsg();

        TVObject tv1 = new TVObject();
        TVObject tv2 = new TVObject();
        //String body ="00000008";
        tv1.setType(1);
        tv1.setValue("0010");

        tv2.setType(2);
        tv2.setValue(String.valueOf(System.currentTimeMillis()));
        TVObject tv3 = new TVObject();
        tv3.setType(3);
        tv3.setValue(ID.randomUUID());
        TVObject tv4 = new TVObject();
        tv4.setType(4);
        tv4.setValue("0001");
        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);
        header.add(tv3);
        header.add(tv4);
        msg.setHeader(header);
        msg.setBody("dsfdsfsfsfs");
        return msg;
    }

    public  static  CBMsg fffMsg()
    {
        CBMsg msg = new CBMsg();


        TVObject tv1 = new TVObject();
        TVObject tv2 = new TVObject();
        TVObject tv3 = new TVObject();

        String body ="bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        tv1.setType(1);
        tv1.setValue("1111111111");

        tv2.setType(2);
        tv2.setValue("222222222222222");


        tv2.setType(3);
        tv2.setValue("33333333333333333333333333333");

        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);

        msg.setHeader(header);
        msg.setBody(body);


        //ByteBuf mgbf = msg.msgToProtocol();

        return msg;
    }
}
