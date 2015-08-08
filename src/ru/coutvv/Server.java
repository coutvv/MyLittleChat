package ru.coutvv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
	ObjectInputStream input;
	
	public Server() throws IOException {
		server = new ServerSocket(4040);
		
	}

	
	public void start() {
		Resender bitch = null;
		try {
			Socket friend = server.accept();
			output = new ObjectOutputStream(friend.getOutputStream());
			input = new ObjectInputStream(friend.getInputStream());
			
			Scanner scan = new Scanner(System.in);
			bitch = new Resender(); 
			bitch.start();
			System.out.println("Thats started");
			while(true) {
				String sendMsg = scan.nextLine();
				output.writeObject(sendMsg);			
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bitch.stop();
				server.close();
				output.close();
				input.close();
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
	

	/**
	 * Класс пишущий в консоль то, что получает с клиента
	 * @author Jane Dow
	 *
	 */
	private class Resender extends Thread {
		private boolean stop = false;
		public void stopIt() {
			stop = true;
		}
		
		public void run() {
			while(!stop){
				try {
					String s = (String) input.readObject();
					System.out.println(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
