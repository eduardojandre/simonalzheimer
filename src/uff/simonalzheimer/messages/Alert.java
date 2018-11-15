package uff.simonalzheimer.messages;

import java.io.Serializable;

public class Alert implements Serializable{

	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
