package ru.coutvv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			server = new ServerSocket(4040);
			
			while(true) {
				Socket sok = server.accept();
				output = new ObjectOutputStream(sok.getOutputStream());
				output.writeObject("fuckyou");
				System.out.println("1 write!");
				
				input = new ObjectInputStream(sok.getInputStream());
				String text = (String) input.readObject();
				
				System.out.println(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				input.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
