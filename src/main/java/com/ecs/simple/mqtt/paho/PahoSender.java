package com.ecs.simple.mqtt.paho;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.ecs.simple.mqtt.Connection;
import com.ecs.simple.mqtt.Constants;
import com.ecs.simple.mqtt.Sender;

public class PahoSender extends Sender {

	private MqttClient client;

	public PahoSender() {
		super(Connection.CLOUD_MQTT_CONNECTION);
	}
	
	@Override
	public void connect() throws Exception {
		client = new MqttClient("tcp://" + getConnection().getHost() + ":" + getConnection().getPort(),"fake_id");
	      
	      MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(getConnection().getUsername());
			options.setPassword(getConnection().getPassword().toCharArray());
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
		sender.sendMessage(Constants.TOPIC_NAME1, "Hello".getBytes());
	}	

}
