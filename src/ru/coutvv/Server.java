package ru.coutvv;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		OutputStream output = null;
		try {
			server = new ServerSocket(4040);
			
			while(true) {
				Socket sok = server.accept();
				output = sok.getOutputStream();
				output.write(1);
				System.out.println("1 write!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
