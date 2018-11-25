package uff.simonalzheimer.messages;

import java.io.Serializable;


public class Alert implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 186420945706924584L;
	private String timeStamp;
    private String message;
    public Alert() {}

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Alert(String timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }
    @Override
    public boolean equals(Object object)
    {
    	if(object instanceof  Alert) {
    		Alert a=(Alert) object;
    		return a.message.equals(this.message);
    	}
    	return false;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
