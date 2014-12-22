package com.smtp.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SMTPClient {
	
	//Just putting this here
	public static void main(String args[]){
		Socket socket = null;
		DataOutputStream os = null;
		DataInputStream is = null;
		
		try {
			socket = new Socket("localhost",8111);
			os = new DataOutputStream(socket.getOutputStream());
			is = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (socket != null && os != null && is != null){
			try {
				os.writeBytes("HELO\n");    
				os.writeBytes("MAIL From: k3is@fundy.csd.unbsj.ca\n");
				os.writeBytes("RCPT To: k3is@fundy.csd.unbsj.ca\n");
				os.writeBytes("DATA\n");
				os.writeBytes("From: k3is@fundy.csd.unbsj.ca\n");
				os.writeBytes("Subject: testing\n");
				os.writeBytes("Hi there\n"); // message body
				os.writeBytes("\n.\n");
				os.writeBytes("QUIT");
				
				String message;
				while((message = is.readLine()) != null){
					System.out.println("Server says: "+message);
					if(message.indexOf("Ok") != -1){
						break;
					}
				}
				os.close();
				is.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
