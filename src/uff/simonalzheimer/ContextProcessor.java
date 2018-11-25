package uff.simonalzheimer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.udi.core.SddlLayer;
import uff.simonalzheimer.messages.Alert;
import uff.simonalzheimer.messages.ContextRule;
import uff.simonalzheimer.messages.Registration;
import uff.simonalzheimer.messages.State;
import uff.simonalzheimer.messages.Registration.ClientType;

public class ContextProcessor {
	public ContextProcessor(SddlLayer sddlLayer) {
		caregivers=new ArrayList<Client>();
		rules=new ArrayList<ContextRule>();
		this.sddlLayer=sddlLayer;
		rules.add(new ContextRule());
		state=new State();
	}
	private State state;
	private SddlLayer sddlLayer;
	private List<Client> caregivers;
	private List<ContextRule> rules;
	
	public void runRules() {
		State s=getState();
		for (ContextRule r : rules) {
			r.runRule(s, caregivers);
		}
	}
	public  synchronized void setState(State s) {
		state.isFridgeOpen=s.isFridgeOpen;
		state.isTvOn=s.isTvOn;
		state.actions=s.actions;
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
					setState((State)rawData);
					runRules();
				}else {
					System.out.println("Server are not ready for this message.");
				}
			}
		}
	}
	
}
