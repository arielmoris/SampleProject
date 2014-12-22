package com.socket.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClientSocket {

	private static Socket socket;
	private static BufferedReader br;
	private static PrintWriter out;

	public static void main(String args[]) throws IOException {
		final String host = "localhost";
		final int portNumber = 8111;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);
		socket = new Socket(host, portNumber);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
		Thread t = new Thread(new IncomingReader());
		t.start();
	}

	public static class IncomingReader implements Runnable{
		@Override
		public void run() {
			try {
				System.out.println("Im Running...");
				String message;
				while((message = br.readLine()) != null){
					System.out.println(message);
					/*out.write("ECHO|");*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
