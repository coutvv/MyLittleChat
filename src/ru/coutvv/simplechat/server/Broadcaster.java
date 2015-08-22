package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Broadcaster extends Thread {

	private List<Resender> clients = new ArrayList<Resender>(); 
	
	
	public Broadcaster() {

	}
	
	public void run() {
		while(true) {
			for(Resender client: clients) {
				if(client!= null && client.isNewMsg()){
					broadcast(client.getMsg(), client);
				}
			}
		}
	}
	
	public void add(Socket s) {
		try {
			Resender client = new Resender(new ObjectInputStream(s.getInputStream()), new ObjectOutputStream(s.getOutputStream()));
			client.start();
			clients.add(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void broadcast(String msg, Resender source) {
		System.out.println("броаткастим от " + source.getId());
		for(Resender client: clients) {
			if(client != source) {
				System.out.println("Пишем");
				client.write(msg);
			}
		}
	}
}
