package uff.simonalzheimer;

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


public class SensorsSimulator implements NodeConnectionListener  {
	public static JList<String> actionList;
	public static String[] actions = {"sleeping", "watchingTv", "cooking", "reading"};
	public static JCheckBox tvIsOn;
	public static JCheckBox fridgeIsOpen;
	public static State state;
	
	private static String gatewayIP = "127.0.0.1";
	private static int gatewayPort = 5500;
	private MrUdpNodeConnection connection;
	
	public SensorsSimulator() {
		InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
		try {
			connection = new MrUdpNodeConnection();
			connection.addNodeConnectionListener(this);
			connection.connect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void setState() {
		state.isFridgeOpen=fridgeIsOpen.isSelected();
		state.isTvOn=tvIsOn.isSelected();
		state.actions=actionList.getSelectedValues();
	}
	public static synchronized State getState() {
		return state;
	}
	public void sendMsg(String msg) {
		ApplicationMessage message = new ApplicationMessage();
		
		message.setContentObject(msg);
		
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("--Starting sensors simulator--");

		state=new State();
		setFrame();
		SensorsSimulator c=new SensorsSimulator();
		Gson gson = new Gson();
		while(true) {
			
			c.sendMsg(gson.toJson(state));
			Thread.sleep(5000);
		}
	}
	public static void setFrame() {
		JFrame frame=new JFrame("Simon Alzheimer Sensor Simulator");
		
		JPanel framePanel=new JPanel();
		JPanel actionPanel=new JPanel();
		JPanel sensorsPanel=new JPanel();
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
	@Override
	public void connected(NodeConnection arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disconnected(NodeConnection arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void internalException(NodeConnection arg0, Exception arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void newMessageReceived(NodeConnection arg0, Message arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reconnected(NodeConnection arg0, SocketAddress arg1, boolean arg2, boolean arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsentMessages(NodeConnection arg0, List<Message> arg1) {
		// TODO Auto-generated method stub
		
	}
}
