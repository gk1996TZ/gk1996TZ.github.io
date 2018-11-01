package com.muck.jt809;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;

public class Message implements Serializable{
	private static final long serialVersionUID = 3605222890858710149L;
	
	public static final int MSG_HEAD = 0x5b; //头标识 
	  public static final int MSG_TAIL = 0x5d;  //尾标识
	  public static final int MSG_LENGTH=26;//固定长度 不包含数据体长度
	  public static final int MSG_AVAILABEL_LENGTH=22;//用于CRC校验的长度 去掉头尾标识和校验码字节长度
	  private int msgLength;//数据长度（包括头标识、数据头、数据体和尾标识）
	  private static int msgGnsscenteridIN=0;//报文序列自增号
	  private int msgSN;//报文序列号
	  private int msgId;//业务数据类型
	  private int msgGnsscenterid;//下级平台接入码
	  private byte[] versionFlag = {1,0,0};  //协议版本号标识
	  private byte encryptFlag;//报文加密标识：0.标识报文不加密 1.表示报文加密
	  
	  private int encryptKey;//数据加密的秘钥,长度为四个字节
	  
	  private ByteBuf msgBody;//数据体
	  
	  private int crcCode;//校验码 
	  
	  private int crc16code;

	public int getCrc16code() {
		return crc16code;
	}

	public void setCrc16code(int crc16code) {
		this.crc16code = crc16code;
	}



	

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public int getMsgSN() {
		return msgSN;
	}

	public void setMsgSN(int msgSN) {
		this.msgSN = msgSN;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		synchronized ((Integer)msgGnsscenteridIN) {
			if(msgGnsscenteridIN == Integer.MAX_VALUE){  
				msgGnsscenteridIN = 0;  
            }  
		}
		this.msgSN = ++msgGnsscenteridIN;  
		this.msgId = msgId;
	}

	public int getMsgGnsscenterid() {
		return msgGnsscenterid;
	}

	public void setMsgGnsscenterid(int msgGnsscenterid) {
		this.msgGnsscenterid = msgGnsscenterid;
	}

	public byte[] getVersionFlag() {
		return versionFlag;
	}

	public void setVersionFlag(byte[] versionFlag) {
		this.versionFlag = versionFlag;
	}

	public byte getEncryptFlag() {
		return encryptFlag;
	}

	public void setEncryptFlag(byte encryptFlag) {
		this.encryptFlag = encryptFlag;
	}

	public int getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(int encryptKey) {
		this.encryptKey = encryptKey;
	}

	public ByteBuf getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(ByteBuf msgBody) {
		this.msgBody = msgBody;
	}
	
	

	public int getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	@Override
	public String toString() {
		return "Message [msgLength=" + msgLength + ", msgSN=" + msgSN + ", msgId=" + msgId + ", msgGnsscenterid="
				+ msgGnsscenterid + ", versionFlag=" + Arrays.toString(versionFlag) + ", encryptFlag=" + encryptFlag
				+ ", encryptKey=" + encryptKey + ", msgBody=" + msgBody + ", crcCode=" + crcCode + ", crc16code="
				+ crc16code + "]";
	}

	

	
	
	  
	  
}
