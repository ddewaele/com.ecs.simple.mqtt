package com.ecs.simple.mqtt.fusesource;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import com.ecs.simple.mqtt.Connection;
import com.ecs.simple.mqtt.Constants;
import com.ecs.simple.mqtt.Receiver;

public class FuseSourceReceiver extends Receiver {

	private MQTT mqtt = new MQTT();
	
	public FuseSourceReceiver() {
		super(Connection.CLOUD_MQTT_CONNECTION);
	}

	@Override
	public void connect() throws Exception {
		//No client needs to be set.
		mqtt.setHost(getConnection().getHost(),getConnection().getPort());
		mqtt.setUserName(getConnection().getUsername());
		// No hashing is required.
		mqtt.setPassword(getConnection().getPassword());		

		mqtt.setTracer(new SystemOutTracer());
	
	}

	@Override
	public void receive(String topicName) throws Exception {
		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();
		
		Topic[] topics = {new Topic(topicName, QoS.AT_LEAST_ONCE)};
		
		connection.subscribe(topics);

		System.out.println("Receiving .....");
		Message message = connection.receive();
		System.out.println("Found message : " + message.getTopic());
		byte[] payload = message.getPayload();
		
		System.out.println("Found payload : " + new String(payload));
		// process the message then:
		message.ack();		
	}
	
	public static void main(String[] args) throws Exception {
		FuseSourceReceiver fuseSourceReceiver = new FuseSourceReceiver();
		fuseSourceReceiver.connect();
		fuseSourceReceiver.receive(Constants.TOPIC_NAME1);
	}

}
