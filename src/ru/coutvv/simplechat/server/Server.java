package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * сервер, ловит клиентов, мэйн тут
 * @author Jane Dow
 *
 */
public class Server {
	//номер порта по которому будем коннектиться
	private static final int PORT = 4040;
	
	ServerSocket server;
	
	public Server() throws IOException {
		server = new ServerSocket(PORT);
		
	}

	
	public void start() {
		try {
			Broadcaster broadcaster = new Broadcaster();
			broadcaster.start();
			
			while(true) {
				//Ждём пока подключится клиент
				Socket client = server.accept();
				System.out.println("Подключился клиент " + client.hashCode());
				broadcaster.add(client);			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
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
