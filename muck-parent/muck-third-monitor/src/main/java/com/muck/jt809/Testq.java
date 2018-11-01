package com.muck.jt809;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Testq {
public static void main(String[] args) {
	//System.out.println(5b);
	String a=Integer.toHexString(11& 0xff);
	System.out.println(a);
	ByteBuf bf=Unpooled.buffer();
	int aa=0x9a;
	System.out.println((byte)aa);
	byte[] src={Byte.parseByte("12",16),Byte.parseByte("00",16)};
	bf.writeBytes(src);
	System.out.println(bf.readShort());
	System.out.println(Integer.parseInt("9a",16));
	transfer();
	String str=Integer.toBinaryString(4);
	 StringBuffer sb=new StringBuffer(str);
	  if(str.length()<32){
		  for(int i=0;i<32-str.length();i++){
			  sb.insert(0, '0');
		  }
	  }
	System.out.println(Byte.parseByte(sb.charAt(29)+""));  
	System.out.println(sb.toString());
}

public static ByteBuf transfer(){
	ByteBuf out=Unpooled.buffer();
	StringBuffer sb=new StringBuffer();
	sb.append("5b");
	sb.append("00");
	sb.append("00");
	sb.append("00");
	sb.append("5a");
	sb.append("02");
	sb.append("00");
	sb.append("00");
	sb.append("70");
	sb.append("26");
	String str=sb.toString()
	.replace("5a01", "5b")
	.replace("5a02", "5a")
	.replace("5e01", "5d")
	.replace("5e01", "5e")
	;
	System.out.println(str);
	//将字符串以4个长度的形式截取存放到字节数组里面
	for(int i=0;i<str.length();i=i+2){
		out.writeByte(Byte.parseByte(str.substring(i, i+2),16));
	}
	return out;
}
/**将字节转换成十六进制*/
public String byteToHex(byte bt){
	String hex= Integer.toHexString(bt & 0xff);
	if(hex.length()==1){
		hex="0"+hex;
	}
	return hex;
}
}
