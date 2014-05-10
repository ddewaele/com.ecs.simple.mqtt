package com.ecs.simple.mqtt.fusesource;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecs.simple.mqtt.Connection;
import com.ecs.simple.mqtt.Constants;
import com.ecs.simple.mqtt.Receiver;

public class FuseSourceReceiver extends Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(FuseSourceReceiver.class);
	
	private MQTT mqtt = new MQTT();
	
	public FuseSourceReceiver() {
		super(Connection.MQTT_HETZNER_CONNECTION);
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

		
		while(true) {
			LOG.info("Receiving .....");
			Message message = connection.receive();
			LOG.info("Found message on topic : " + message.getTopic());
			byte[] payload = message.getPayload();
			
			LOG.info("Found message payload : " + new String(payload));
			// process the message then:
			message.ack();
		}
	}
	
	public static void main(String[] args) throws Exception {
		FuseSourceReceiver fuseSourceReceiver = new FuseSourceReceiver();
		fuseSourceReceiver.connect();
		fuseSourceReceiver.receive(Constants.TOPIC_NAME1);
	}

}
