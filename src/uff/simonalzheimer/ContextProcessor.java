package uff.simonalzheimer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.udi.core.SddlLayer;
import uff.simonalzheimer.messages.Alert;
import uff.simonalzheimer.messages.Condition;
import uff.simonalzheimer.messages.Registration;
import uff.simonalzheimer.messages.State;
import uff.simonalzheimer.messages.Registration.ClientType;
import uff.simonalzheimer.messages.Routine;

public class ContextProcessor {
	public ContextProcessor(SddlLayer sddlLayer) {
		caregivers=new ArrayList<Client>();
		routines=new ArrayList<Routine>();
		this.sddlLayer=sddlLayer;
		state=new State();
	}
	private State state;
	private SddlLayer sddlLayer;
	private List<Client> caregivers;
	private List<Routine> routines;
	public String getKeyValue(State object, String name) {
		Field field;
		try {
			name=name.replace(" ", "");
			name=name.substring(0, 1).toLowerCase() + name.substring(1);
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return (String) field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}    
	}
	
	public void runRules() {
		State s=getState();
		for (Routine r : routines) {
			if(r.isEnabled()) {
				boolean satisfied=true;
				for (Condition<String,String> c : r.getConditions()){
					String key=(String)c.getKey();
					String value=getKeyValue(s, key);
					satisfied= satisfied && (
							(value.equals(c.getValue()) && !c.getNotValueBool()) || 
							(!value.equals(c.getValue()) && c.getNotValueBool())
							);
				}
				if(satisfied) {
					for (String a: r.getActions()) {
						System.out.println("Action: "+ a);
						if(a.contains("Caregiver")) {
							for(Client c: caregivers) {
								Alert al=new Alert();
								al.setMessage(r.getName());
								c.sendMessage(al);
							}
						}
					}	
				}
			}
		}
	}
	public  synchronized void setState(State s) {
		state.activity=s.activity;
		state.bloodPreassure=s.bloodPreassure;
		state.bodyTemperature=s.bodyTemperature;
		state.heartBeat=s.heartBeat;
		state.location=s.location;
	}
	public synchronized State getState() {
		return state;
	}
	
	private void registerClient(Message msg, Serializable rawData) {
		Registration regMsg = (Registration) rawData;
		
		Client regi=new Client();
		regi.setGatewayId(msg.getGatewayId());
		regi.setSenderId(msg.getSenderId());
		regi.setSddlLayer(sddlLayer);
		if(regMsg.getType()==ClientType.Caregiver)
			caregivers.add(regi);
		
		System.out.println(regi.toString());
		
	}
	
	/**
	 * This method is the one you should customize so that the server is capable of 
	 * handling different types of objects.
	 * 
	 * @param msg
	 * @param rawData
	 */
	public void treatDataReceival(Message msg, Serializable rawData) {

		if(rawData instanceof Registration) {
			registerClient(msg,rawData);
		}else {
			if (rawData instanceof String) {
				System.out.println("\nMensagem: " + (String) rawData);	
			}else{
				if (rawData instanceof State) {
					System.out.println(rawData);
					setState((State)rawData);
					runRules();
				}else {
					if(rawData instanceof ArrayList<?>) {
						routines=(ArrayList<Routine>) rawData;
						
					}else {
						System.out.println("Server are not ready for this message.");
					}
				}
			}
		}
	}
	
}
