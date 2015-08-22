package ru.coutvv.simplechat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	
	Socket socket; 
	
	ObjectOutputStream output;
	
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 4040);
	}
	
	public void start() {
		Agent msgWriter = null;
		try {

			output = new ObjectOutputStream(socket.getOutputStream());

			msgWriter = new Agent(new ObjectInputStream(socket.getInputStream())); 
			msgWriter.start();
			
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			
			while(true) {
				String sendMsg = scan.nextLine();
				output.writeObject(sendMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				msgWriter.stopIt();
				socket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			Client cl = new Client();
			cl.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
