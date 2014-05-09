package com.ecs.simple.mqtt.fusesource;
import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;


public class SystemOutTracer extends Tracer {

	@Override
	public void debug(String message, Object... args) {
		System.out.println(message + " " + args);
	}
	
	@Override
	public void onSend(MQTTFrame frame) {
		System.out.println(frame);
	}
	
	@Override
	public void onReceive(MQTTFrame frame) {
		System.out.println(frame);
	}
}
