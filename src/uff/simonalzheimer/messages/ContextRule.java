package uff.simonalzheimer.messages;

import java.util.List;

import uff.simonalzheimer.Client;

public class ContextRule {
	public void runRule(State s, List<Client> caregivers) {
		if(s.isFridgeOpen) {
			Alert a=new Alert();
			a.setMessage("Perigo - Geladeira aberta" );
			for (Client c : caregivers) {
			    c.sendMessage(a);
			}
		}
	}
}
