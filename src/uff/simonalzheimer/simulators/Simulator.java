package uff.simonalzheimer.simulators;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

public class Simulator implements NodeConnectionListener   {
	
	private static String gatewayIP = "127.0.0.1";
	private static int gatewayPort = 5500;
	
	private MrUdpNodeConnection connection;
	
	public Simulator() {
		InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
		try {
			connection = new MrUdpNodeConnection();
			connection.addNodeConnectionListener(this);
			connection.connect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMsg(Serializable content) {
		ApplicationMessage message = new ApplicationMessage();
		
		message.setContentObject(content);
		
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
