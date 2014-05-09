package com.ecs.simple.mqtt;

public abstract class Sender {

	private final Connection connection;
	
	public Sender(Connection connection) {
		this.connection = connection;
	}
	
	public abstract void connect() throws Exception;
	public abstract void sendMessage(String topicName,byte[] payload) throws Exception;

	public Connection getConnection() {
		return connection;
	}
}
