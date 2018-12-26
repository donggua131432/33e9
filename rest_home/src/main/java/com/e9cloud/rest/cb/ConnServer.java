package com.e9cloud.rest.cb;

import com.e9cloud.core.application.InitApp;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/3/3.
 */
public class ConnServer implements  Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ConnServer.class);
    private String host;
    private int port;
    private int re_conn_wait_seconds;
    private int write_wait_seconds;
    private int weight;
    private static ReconnServer reconnServer;
    public ConnServer(String host, int port,int re_conn_wait_seconds,int write_wait_seconds,int weight){
        this.host = host;
        this.port = port;
        this.re_conn_wait_seconds = re_conn_wait_seconds;
        this.write_wait_seconds = write_wait_seconds;
        this.weight = weight;
    }
    @Override
    public void run() {
        boolean isConnSucc = true;
        while(isConnSucc){
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast("framerDecoder", new LengthFieldBasedFrameDecoder(64 * 1024, 0, 4, -4, 0));
                                p.addLast("msgDecoder", new MsgDecoder());
                                p.addLast("ping", new IdleStateHandler(0, write_wait_seconds, 0, TimeUnit.SECONDS));
                                p.addLast("msgEncoder", new MsgEncoder());
                                p.addLast(new CBTcpClientHandler());
                            }
                        });
                Channel channel = bootstrap.connect(host,port).sync().channel();
                InitApp.getInstance().getSchedu().addServer(this.host+":"+this.port,channel,weight);

                // 此方法会阻塞
                channel.closeFuture().sync();
                logger.info(this.host+":"+this.port+"====连接成功");
                isConnSucc = false;
            } catch (Exception e) {
                logger.info("rest error: connection cb failure "+ this.host+":"+this.port);
                logger.info(this.host + ":" + this.port + "====连接失败" + re_conn_wait_seconds + "秒后重试");
            }finally {
                group.shutdownGracefully();
            }
            try {
                if(isConnSucc){
                    Thread.sleep(re_conn_wait_seconds*1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
//    public static void reschedule() {
//        Executors.newScheduledThreadPool(2).schedule("重连", new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    reconnServer.checkConnect();
//                    Thread.sleep(3000);
//                    reschedule();
//                } catch (Exception e) {
//
//                }
//            }
//
//        }, 5, TimeUnit.SECONDS);
//    }
}
