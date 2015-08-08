package ru.coutvv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	Socket socket; 
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 4040);
	}
	
	public void start() {
		try {

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			while(true) {
				String text = (String) input.readObject();
				System.out.println(text);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			new Client().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
