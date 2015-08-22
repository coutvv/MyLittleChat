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

			System.out.println("Thats started");
			
			//������ ����� �����, ������� � ������ ������������ �� ������� ������� � ��������� 
			msgWriter = new Resender(new ObjectInputStream(client.getInputStream())); 
			msgWriter.start();
			
			//��������� ���������
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
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
	
}
