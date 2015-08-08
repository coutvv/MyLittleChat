package ru.coutvv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Простой сервачок на одну бошку
 * @author Jane Dow
 *
 */
public class Server {
	ServerSocket server;
	ObjectOutputStream output;
	
	public Server() throws IOException {
		server = new ServerSocket(4040);
		
	}
	
	public void addMessage(String msg) {
		System.out.println(msg);
	}
	
	public void start() {
		try {
			Socket friend = server.accept();
			output = new ObjectOutputStream(friend.getOutputStream());
			
			Scanner scan = new Scanner(System.in);
			while(true) {
				String sendMsg = scan.nextLine();
				output.writeObject(sendMsg);			
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Server it;
		try {
			it = new Server();
			System.out.println("starting server");
			it.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
