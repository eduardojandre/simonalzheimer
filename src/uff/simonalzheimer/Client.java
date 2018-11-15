package uff.simonalzheimer;

import java.io.Serializable;
import java.util.UUID;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;

public class Client{
	private UUID gatewayId;
	private UUID nodeId;
	public UUID getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
	}
	public UUID getSenderId() {
		return nodeId;
	}
	public void setSenderId(UUID uUID) {
		nodeId = uUID;
	}
	public String toString()
	{
		return "Sender Id: " +nodeId.toString() + " Gateway Id: "+ gatewayId; 
	}
	public void sendMessage(SddlLayer sddlLayer,Serializable content) {
		
		ApplicationMessage appMsg = new ApplicationMessage();
		appMsg.setContentObject(content);
		
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setGatewayId(gatewayId);
		privateMessage.setNodeId(nodeId);
		privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));

		sddlLayer.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
	}

}
