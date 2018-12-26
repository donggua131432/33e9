package com.e9cloud.demo.protocol;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

public class TLVPojo 
{
	private int type; 	//版本号0010（1.0版本）
	private int length;	//序列号 消息流水号sn,应答消息带回,如：AAAABBBB
	private String value;	//32位标示，标示上下文sessionId
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	int getTLVLen()
	{
		return 4 + 4 + value.length();
	}

	public ByteBuffer toBytes()
	{
		
		ByteBuffer  buf =  ByteBuffer.allocate(4+4+value.length());
		buf.putInt(type);
		buf.putInt(length);
		buf.put(value.getBytes());
		
		return buf; 
		
	}

}
