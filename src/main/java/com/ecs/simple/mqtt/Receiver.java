package com.ecs.simple.mqtt;

public abstract class Receiver {

	private final Connection connection;
	
	public Receiver(Connection connection) {
		this.connection = connection;
	}
	
	public abstract void connect() throws Exception;
	public abstract void receive(String topicName) throws Exception;

	public Connection getConnection() {
		return connection;
	}
}
