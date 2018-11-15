package uff.simonalzheimer.messages;

import java.io.Serializable;

public class Registration implements Serializable{
	public enum ClientType {
		Sensor,Patient,Caregiver
	}
	private ClientType type;
	
	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}
	
}
