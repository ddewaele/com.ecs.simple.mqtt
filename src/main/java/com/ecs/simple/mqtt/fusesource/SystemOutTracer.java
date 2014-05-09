package com.ecs.simple.mqtt.fusesource;

import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemOutTracer extends Tracer {

	private static final Logger LOG = LoggerFactory.getLogger(SystemOutTracer.class);

	@Override
	public void debug(String message, Object... args) {
		LOG.debug(message, args);
	}

	@Override
	public void onSend(MQTTFrame frame) {
		LOG.debug("Client Sent:\n" + frame);
	}

	@Override
	public void onReceive(MQTTFrame frame) {
		LOG.debug("Client Received:\n" + frame);
	}
}
