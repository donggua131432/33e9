package com.e9cloud.rest.cb;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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
public  final class CBTcpClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8082"));
//    static final String HOST = System.getProperty("host", "192.168.0.146");
//    static final int PORT = Integer.parseInt(System.getProperty("port", "7894"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void send(String body) throws Exception {

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

            f.channel().writeAndFlush(hbMsg("0010","11111111","6dd7064bd64c46829bb4b1b3ff9f6c52","0001","00001",body));

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public  static CBMsg hbMsg(String v1,String v2,String v3,String v4,String v5,String str)
    {
        CBMsg msg = new CBMsg();

        TVObject tv1 = new TVObject();
        TVObject tv2 = new TVObject();
        TVObject tv3 = new TVObject();
        TVObject tv4 = new TVObject();
        TVObject tv5 = new TVObject();
        String body =str;
        tv1.setType(1);
        tv1.setValue(v1);

        tv2.setType(2);
        tv2.setValue(v2);

        tv3.setType(3);
        tv3.setValue(v3);

        tv4.setType(4);
        tv4.setValue(v4);

        tv5.setType(5);
        tv5.setValue(v5);

        List<TVObject> header = new ArrayList<TVObject>();
        header.add(tv1);
        header.add(tv2);
        header.add(tv3);
        header.add(tv4);
        header.add(tv5);
        msg.setHeader(header);
        msg.setBody(body);

        //ByteBuf mgbf = msg.msgToProtocol();

        return msg;
    }
}
