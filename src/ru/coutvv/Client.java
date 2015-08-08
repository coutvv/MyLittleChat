package ru.coutvv;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		try {
			Socket mainSocket = new Socket("localhost", 4040);
			ObjectInputStream input = new ObjectInputStream( mainSocket.getInputStream());
			
			String text = (String) input.readObject();
			
			System.out.println(text);
			
			ObjectOutputStream ouput =new ObjectOutputStream(mainSocket.getOutputStream());
			
			ouput.writeObject("stupid fucking cunt");
			
			ouput.close();
			input.close();
			mainSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
