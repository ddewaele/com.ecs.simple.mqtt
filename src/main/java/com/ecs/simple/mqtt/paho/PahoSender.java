package com.ecs.simple.mqtt.paho;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecs.simple.mqtt.Connection;
import com.ecs.simple.mqtt.Constants;
import com.ecs.simple.mqtt.Sender;

public class PahoSender extends Sender {

	private static final Logger LOG = LoggerFactory.getLogger(PahoSender.class);
	
	private MqttClient client;

	public PahoSender() {
		super(Connection.MOSQUITTO_TEST_CONNECTION);
	}
	
	@Override
	public void connect() throws Exception {
		client = new MqttClient("tcp://" + getConnection().getHost() + ":" + getConnection().getPort(),"fake_id");
	      
	      MqttConnectOptions options = new MqttConnectOptions();
	      if (getConnection().hasUsernamePassword()) {
			options.setUserName(getConnection().getUsername());
			options.setPassword(getConnection().getPassword().toCharArray());
	      }
	      client.connect(options);
	      
		
	}

	@Override
	public void sendMessage(String topicName, byte[] payload) throws Exception {
	      MqttMessage message = new MqttMessage();
	      message.setPayload(payload);
	      client.publish(topicName, message);
	      client.disconnect();
	}
	

	public static void main(String[] args) throws Exception {
		Sender sender = new PahoSender();
		sender.connect();
		String payload = "Hello @ " + new Date();
		LOG.info("Sending msg");
		sender.sendMessage(Constants.TOPIC_NAME1, payload.getBytes());
		LOG.info("Msg sent ...");
	}	

}
