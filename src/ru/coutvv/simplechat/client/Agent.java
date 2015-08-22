package ru.coutvv.simplechat.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import ru.coutvv.simplechat.data.Message;

public class Agent extends Thread {
	private ObjectInputStream input;
	private boolean stop = false;
	
	public void stopIt() {
		stop = true;
	}
	
	public Agent(ObjectInputStream in) {
		this.input = in;
	}
	
	public void run() {
		while(!stop){
			try {
				Message s = (Message) input.readObject();
				System.out.println(s);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	} 
	
	public void destroy() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
