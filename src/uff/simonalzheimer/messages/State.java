package uff.simonalzheimer.messages;

import java.io.Serializable;

public class State implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1990764436475001476L;
	public String activity;
	public String bloodPreassure;
	public String bodyTemperature;
	public String heartBeat;
	public String location;
	public String toString() {
		return "Activity: "+activity + " | Blood Preassure: "+ bloodPreassure+" | Body Temperature: "+
				bodyTemperature+" | Heart Beat: "+heartBeat+" | Location: "+location;
	}
	
	
}
