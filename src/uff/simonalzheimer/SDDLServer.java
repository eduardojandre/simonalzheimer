package uff.simonalzheimer;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import uff.simonalzheimer.messages.*;
import uff.simonalzheimer.messages.Registration.ClientType;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory.SupportedDDSVendors;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;


public class SDDLServer implements UDIDataReaderListener<ApplicationObject> {
	
	/* The SDDL vendor supported */
    private SupportedDDSVendors supportedDDSVendor;

    /*The SDDL Layer : DDS Abstraction */
    private static SddlLayer sddlLayer;
    
    private static List<Client> caregivers=new ArrayList<Client>();
    
	public SDDLServer () 
	{
		System.out.println("SDDLServer: starting...");
		
		/*read configuration file*/
		System.out.println("SDDLServer: reading configuration file...");
		readConfigurationFile();
		
	    /*create the SDDL layer with a Subscriber listener*/
		System.out.println("SDDLServer: initializing DDS and SDDL...");
	    sddlLayer = UniversalDDSLayerFactory.getInstance(supportedDDSVendor);
	    sddlLayer.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);
	    sddlLayer.createPublisher();
	    sddlLayer.createSubscriber();
	    Object receiveTopic = sddlLayer.createTopic(Message.class, Message.class.getSimpleName());
	    Object sendTopic = sddlLayer.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
	    sddlLayer.createDataReader(this, receiveTopic);
	    sddlLayer.createDataWriter(sendTopic);
	}
		
	public static void main(String[] args) {
		new SDDLServer();
		System.out.println("SDDLServer: started successfully.");
	}
	
	@Override
	public void onNewData(ApplicationObject topicSample) {
		Message msg = null;
		if (topicSample instanceof Message) {
			msg = (Message) topicSample;
			Serializable rawData = Serialization.fromJavaByteStream(msg.getContent());
			treatDataReceival(msg, rawData);
		}
	}
	private void registerClient(Message msg, Serializable rawData) {
		Registration regMsg = (Registration) rawData;
		
		Client regi=new Client();
		regi.setGatewayId(msg.getGatewayId());
		regi.setSenderId(msg.getSenderId());
		
		if(regMsg.getType()==ClientType.Caregiver)
			caregivers.add(regi);
		
		System.out.print(regi.toString());
		
		Alert aler=new Alert();
		aler.setMessage("Registrado");
		
		regi.sendMessage(sddlLayer, aler);
	}
	/* Private Methods */
	/**
	 * This method is the one you should customize so that the server is capable of 
	 * handling different types of objects.
	 * 
	 * @param msg
	 * @param rawData
	 */
	private void treatDataReceival(Message msg, Serializable rawData) {

		if(rawData instanceof Registration) {
			registerClient(msg,rawData);
		}else {
			if (rawData instanceof String) {
				System.out.println("\nMensagem: " + (String) rawData);	
			}else {
				System.out.println("Server are not ready for this message.");
			}
			
		}
	}

	private void readConfigurationFile () {
		supportedDDSVendor = SupportedDDSVendors.OpenSplice;
	}
}
