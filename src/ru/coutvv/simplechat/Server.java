package ru.coutvv.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * ������� �������� �� ���� �����
 * @author Jane Dow
 *
 */
public class Server {
	//����� ����� �� �������� ����� ������������
	private static final int PORT = 4040;
	
	ServerSocket server;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public Server() throws IOException {
		server = new ServerSocket(PORT);
		
	}

	
	public void start() {
		Resender msgWriter = null;
		try {
			//��� ���� ����������� ������
			Socket client = server.accept();
			//���� ������ ����� ������
			output = new ObjectOutputStream(client.getOutputStream());
			input = new ObjectInputStream(client.getInputStream());
			
			//������ ����� �����, ������� � ������ ������������ �� ������� ������� � ��������� 
			msgWriter = new Resender(); 
			msgWriter.start();
			
			//��������� ���������
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Thats started");
			while(true) {
				String sendMsg = scan.nextLine();
				output.writeObject(sendMsg); //���������� ����������			
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				msgWriter.stopIt();
				server.close();
				output.close();
				input.close();
			} catch (IOException e) {
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
	 * ����� ������� � ������� ��, ��� �������� � �������
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
