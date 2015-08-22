package ru.coutvv.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * ����� ������� �������� ��������� ������� ��������
 *
 */
public class Resender extends Thread {
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean stop = false;
	
	public void stopIt() {
		stop = true;
	}
	
	public boolean isStop(){
		return stop;
	}
	
	public Resender(ObjectInputStream in, ObjectOutputStream out) {
		this.input = in;
		this.output = out;
	}
	
	public void run() {
		while(!stop){
			try {
				String s = (String) input.readObject();
				isMsg = true;
				msg = s;
			} catch (Exception e) {
				stop = true;
			} 
		}
		
	} 
	
	private boolean isMsg;
	private String msg;
	
	public boolean isNewMsg() {
		return isMsg;
	}
	
	public String getMsg() {
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
	 * ����� ��������� �������
	 * @param message
	 */
	public void write(String message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			stop = true;
		}
	}
}
