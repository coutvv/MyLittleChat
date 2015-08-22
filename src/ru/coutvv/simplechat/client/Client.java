package ru.coutvv.simplechat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import ru.coutvv.simplechat.data.Message;


public class Client {
	
	Socket socket; 
	
	ObjectOutputStream output;
	
	String ip = "localhost";
	int port = 4040;
	
	String clientName = "Anon";
	public void setClientName(String name) {
		this.clientName = name;
	}
	public Client() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
	}
	
	public void start() {
		Agent msgWriter = null;
		try {

			output = new ObjectOutputStream(socket.getOutputStream());

			msgWriter = new Agent(new ObjectInputStream(socket.getInputStream())); 
			msgWriter.start();
			Message login = new Message(clientName, Message.TYPE_LOGIN, clientName);
			output.writeObject(login);
			
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			
			while(true) {
				String sendMsg = scan.nextLine();
				Message msg = new Message(sendMsg, Message.TYPE_MESSAGE, clientName);
				System.out.println("отправлено " + msg.getAuthor());
				output.writeObject(msg);
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
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		try {
			Client cl = new Client();

			System.out.println("Назовитесь:");
			String name = scan.nextLine();
			cl.setClientName(name);
			
			cl.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
