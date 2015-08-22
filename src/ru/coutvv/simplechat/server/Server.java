package ru.coutvv.simplechat.server;

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
	
	public Server() throws IOException {
		server = new ServerSocket(PORT);
		
	}

	
	public void start() {
		Resender msgWriter = null;
		try {
			Broadcaster broadcaster = new Broadcaster();
			broadcaster.start();
			
			while(true) {
				//��� ���� ����������� ������
				Socket client = server.accept();
				System.out.println("����������� ������ " + client.hashCode());
				broadcaster.add(client);			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				msgWriter.stopIt();
				server.close();
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
