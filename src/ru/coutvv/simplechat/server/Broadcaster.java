package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ru.coutvv.simplechat.data.Message;


public class Broadcaster extends Thread {

	private List<ClientConnection> clients = new ArrayList<ClientConnection>(); 
	
	
	public void run() {
		while(true) {
			for(ClientConnection client: clients) {
				if(client!=null && client.isStop()) {
					System.out.println("������� �������������� �������");
					clients.remove(client);
					break;
				}
				if(client!= null && client.isNewMsg()){
					broadcast(client.getMsg(), client);
				}
			}
		}
	}
	
	/**
	 * ��������� ������ �������
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
		System.out.println("����������� �� " + source.getId());
		for(ClientConnection client: clients) {
			if(client!= null && client != source) {
				System.out.println("�����");
				client.write(msg);
			}
		}
	}
}
