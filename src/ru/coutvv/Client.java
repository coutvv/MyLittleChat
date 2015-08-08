package ru.coutvv;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		try {
			Socket mainSocket = new Socket("localhost", 4040);
			InputStream input = mainSocket.getInputStream();
			int text = input.read();
			
			System.out.println(text);
			input.close();
			mainSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
