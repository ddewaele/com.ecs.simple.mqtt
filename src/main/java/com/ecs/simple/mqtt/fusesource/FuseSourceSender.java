package com.ecs.simple.mqtt.fusesource;

import java.util.Date;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import com.ecs.simple.mqtt.Connection;
import com.ecs.simple.mqtt.Constants;
import com.ecs.simple.mqtt.Sender;

public class FuseSourceSender extends Sender {

	private MQTT mqtt = new MQTT();

	public FuseSourceSender() {
		super(Connection.MQTT_HETZNER_CONNECTION);
	}
	
	@Override
	public void connect() throws Exception {
		
		mqtt.setHost(getConnection().getHost(),getConnection().getPort());
		
		if(getConnection().hasUsernamePassword()) { 
			mqtt.setUserName(getConnection().getUsername());
			// No hashing is required.
			mqtt.setPassword(getConnection().getPassword());
		}

		mqtt.setTracer(new SystemOutTracer());

	}

	@Override
	public void sendMessage(String topicName,byte[] payload) throws Exception {
		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();
		connection.publish(topicName,payload, QoS.AT_LEAST_ONCE, false);
	}

	public static void main(String[] args) throws Exception {
		Sender sender = new FuseSourceSender();
		sender.connect();
		for (int i=0 ; i<3 ; i++) {
			String payload = "Sent from Java @ " + new Date();
			sender.sendMessage(Constants.TOPIC_NAME1, payload.getBytes());
		}
	}
}
