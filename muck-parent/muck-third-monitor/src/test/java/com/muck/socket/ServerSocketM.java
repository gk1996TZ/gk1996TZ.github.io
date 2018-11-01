package com.muck.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSocketM {
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket=new ServerSocket(9998,12,InetAddress.getByName("192.168.0.111"));
			System.out.println(serverSocket.getInetAddress());
			while(true){
				Socket socket=serverSocket.accept();
				int p=socket.getPort();
				InetAddress add=socket.getInetAddress();
				System.out.println(add+":"+p);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}).start();*/
			
		
	}
}
