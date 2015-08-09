package ru.coutvv.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	
	Socket socket; 
	
	ObjectOutputStream output;
	ObjectInputStream input;
	
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 4040);
	}
	
	public void start() {
		Resender msgWriter = null;
		try {

			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			msgWriter = new Resender(); 
			msgWriter.start();
			
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			while(true) {
				String sendMsg = scan.nextLine();
				output.writeObject(sendMsg);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
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
	
	/**
	 * Печатыватель того что придёт из сокета
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
