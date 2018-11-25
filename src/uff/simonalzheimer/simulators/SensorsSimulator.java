package uff.simonalzheimer.simulators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import uff.simonalzheimer.messages.State;


public class SensorsSimulator extends Simulator  {
	
	public static State state;
	
	public static void main(String[] args) throws Exception {
		System.out.println("--Starting sensors simulator--");

		state=new State();
	
		SensorsSimulator c=new SensorsSimulator();
		File file = new File("stateSequence.csv"); 
		BufferedReader br = new BufferedReader(new FileReader(file));   
		String st;
		String[] temp;
		//header
		br.readLine();
		while(true) {
			if((st = br.readLine()) == null) {
				br.close();
			 	br=new BufferedReader(new FileReader(file));
			 	br.readLine();
			 	st=br.readLine(); 
			} 
			temp=st.split(";");
			state.activity=temp[0];
			state.bloodPreassure=temp[1];
			state.bodyTemperature=temp[2];
			state.heartBeat=temp[3];
			state.location=temp[4];
			
			c.sendMsg(state);
			Thread.sleep(3000);
		}
	}
		
}
