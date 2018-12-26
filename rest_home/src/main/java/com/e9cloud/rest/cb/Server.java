package com.e9cloud.rest.cb;

import io.netty.channel.Channel;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2016/3/1.
 */
public class Server {
    public String ip;
    public int weight;
    public Channel channel;


    public Server(){

    }

    public Server(String ip, Channel channel, int weight) {
        super();
        this.ip = ip;
        this.weight = weight;
        this.channel = channel;
      
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}

