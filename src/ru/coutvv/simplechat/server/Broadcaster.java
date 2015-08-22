package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.coutvv.simplechat.data.Message;


public class Broadcaster extends Thread {

	private List<ClientConnection> clients = new ArrayList<ClientConnection>(); 
	
	
	public void run() {
		
		while(true) {

			Iterator<ClientConnection> it = clients.iterator();
			while(it.hasNext()) {
				ClientConnection client = (ClientConnection) it.next();
				if(client!= null && client.isNewMsg()){
					Message msg = client.getMsg();
					if(msg.getType() == Message.TYPE_LOGIN) {
						System.out.println("устанавливаем имя клиенту");
						client.setClientName(msg.getMsg());
					} else if(msg.getType() == Message.TYPE_LOGOUT) {
						client.stopIt();
					}
					System.out.println("ну тип отправляем всем посонам");
					broadcast(msg, client);
				}
				if(client!=null && client.isStop()) {
					System.out.println("Удаляем отключившегося клиента");
					clients.remove(client);
					break;
				}
			}
		}
	}
	
	/**
	 * Добавляем нового клиента
	 * @param s
	 */
	public void add(Socket s) {
		try {
			ClientConnection client = new ClientConnection(new ObjectInputStream(s.getInputStream()), new ObjectOutputStream(s.getOutputStream()));
			client.start();
			clients.add(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void broadcast(Message msg, ClientConnection source) {
		System.out.println("броаткастим от " + source.getId());
		for(ClientConnection client: clients) {
			if(client!= null && client != source) {
				System.out.println("Пишем");
				client.write(msg);
			}
		}
	}
}
