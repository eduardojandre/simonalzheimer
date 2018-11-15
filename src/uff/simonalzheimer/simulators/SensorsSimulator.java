package uff.simonalzheimer.simulators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.google.gson.Gson;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import uff.simonalzheimer.messages.State;


public class SensorsSimulator extends Simulator  {
	public static JList<String> actionList;
	public static String[] actions = {"sleeping", "watchingTv", "cooking", "reading"};
	public static JCheckBox tvIsOn;
	public static JCheckBox fridgeIsOpen;
	public static State state;
	
	
	public static synchronized void setState() {
		state.isFridgeOpen=fridgeIsOpen.isSelected();
		state.isTvOn=tvIsOn.isSelected();
		state.actions=actionList.getSelectedValues();
	}
	public static synchronized State getState() {
		return state;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("--Starting sensors simulator--");

		state=new State();
		setFrame();
		SensorsSimulator c=new SensorsSimulator();
	
		while(true) {
			
			c.sendMsg(state);
			Thread.sleep(10000);
		}
	}
	public static void setFrame() {
		JFrame frame=new JFrame("Simon Alzheimer Sensor Simulator");
		
		JPanel framePanel=new JPanel();
		JPanel actionPanel=new JPanel();
		JPanel sensorsPanel=new JPanel();
		JPanel locationPanel=new JPanel();
		JLabel locationLabel=new JLabel("Room: ");
		JComboBox<String> locationCombo=new JComboBox<String>(new String[]{"bedroom","Kitchen","livingroom","bathroom"});
		locationLabel.setLabelFor(locationCombo);
		locationPanel.add(locationLabel);
		locationPanel.add(locationCombo);
		
		
		JButton confirmChanges=new JButton("Confirm");
		confirmChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setState();
			}
		});
		JLabel actionLabel=new JLabel("Current actions");
		tvIsOn=new JCheckBox("Tv is on?");
		fridgeIsOpen=new JCheckBox("Fridge is on?");
		sensorsPanel.setLayout(new BoxLayout(sensorsPanel,BoxLayout.Y_AXIS));
		sensorsPanel.add(locationPanel);
		sensorsPanel.add(tvIsOn);	
		sensorsPanel.add(fridgeIsOpen);
		actionList = new JList<String>(actions);
		actionLabel.setLabelFor(actionList);
		actionPanel.add(actionLabel);
		actionPanel.add(actionList);
		framePanel.add(actionPanel);
		framePanel.add(sensorsPanel);
		framePanel.add(confirmChanges);
		frame.add(framePanel);


	    frame.pack();
	    frame.setVisible(true);
	}
	
}
