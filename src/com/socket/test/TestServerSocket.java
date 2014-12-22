package com.socket.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerSocket {
	

	public static void main(String[] args) {
		final int portNumber = 8111;
		try {
			System.out.println("Creating server socket on port " + portNumber);
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Got a connection!");
				Thread t = new Thread(new MyClientRunnable(socket));
				t.start();
				Thread t2 = new Thread(new MyWriter(socket));
				t2.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class MyClientRunnable implements Runnable{
		

		private BufferedReader br;
		private PrintWriter pw;
		public MyClientRunnable(Socket socket) {
			try {
				OutputStream os = socket.getOutputStream();
				pw = new PrintWriter(os, true);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				pw.println("Hello"+'\0');
				String message;
				while((message = br.readLine()) != null){
					System.out.println("read :"+message);
					//pw.println("Hello ");
				}
				Thread.currentThread().stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static class MyWriter implements Runnable{
		int count;
		private BufferedReader br;
		private PrintWriter pw;
		public MyWriter(Socket socket) {
			try {
				OutputStream os = socket.getOutputStream();
				pw = new PrintWriter(os, true);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				while(true){
					pw.println(String.valueOf(System.currentTimeMillis())+'\0');
					System.out.println(String.valueOf(System.currentTimeMillis())+'\0');
					Thread.sleep(10000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
