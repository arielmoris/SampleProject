package com.smtp.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPServer {
	public static void main(String args[]){
		ServerSocket serverSocket = null;
		DataInputStream is = null;
		PrintStream os = null;
		String message;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(8111);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){
			try {
				socket = serverSocket.accept();
				System.out.println("New Client!");
				is = new DataInputStream(socket.getInputStream());
				os = new PrintStream(socket.getOutputStream());
				while((message = is.readLine()) != null){
					os.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
