package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.coutvv.simplechat.data.Message;


/**
 * Класс который является носителем инаутов клиентов
 *
 */
public class ClientConnection extends Thread {
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean stop = false;
	
	private String clientName = "Anon";
	
	public String getClientName(){
		return clientName;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void stopIt() {
		stop = true;
	}
	
	public boolean isStop(){
		return stop;
	}
	
	public ClientConnection(ObjectInputStream in, ObjectOutputStream out) {
		this.input = in;
		this.output = out;
	}
	
	public void run() {
		while(!stop){
			try {
				Message s = (Message) input.readObject();
				isMsg = true;
				msg = s;
			} catch (Exception e) {
				stop = true;
			} 
		}
		
	} 
	
	private boolean isMsg;
	private Message msg;
	
	public boolean isNewMsg() {
		return isMsg;
	}
	
	public Message getMsg() {
		isMsg = false;
		return msg;
	}
	
	
	public void destroy() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Пишем сообщение клиенту
	 * @param message
	 */
	public void write(Message message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			stop = true;
		}
	}
}
