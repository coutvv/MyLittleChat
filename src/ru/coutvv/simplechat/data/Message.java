package ru.coutvv.simplechat.data;

public class Message {
	public static final int TYPE_LOGIN = 0;
	public static final int TYPE_LOGOUT = 1;
	public static final int TYPE_MESSAGE = 2;
	
	private int type = TYPE_MESSAGE;
	private String msg = "";
	
	public Message(String msg){
		this.msg = msg;
	}
	
	public Message(String msg, int type) {
		this.msg = msg;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}
}
