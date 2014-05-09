package com.ecs.simple.mqtt;

public class Connection {

	public static final Connection CLOUD_MQTT_CONNECTION = new Connection("m10.cloudmqtt.com",19104,"ddw","ddw");
	public static final Connection TELEMETRY_MQTT_CONNECTION = new Connection("q.m2m.io",4483,"4230470327124ed3a71117fff17585d4","3i9h44tzsh","7223c48dfa131bab41c4f8789b5da2e5");
	
	private final String host;
	private final int port;
	private final String username;
	private final String password;
	private String clientId;

	private Connection(String host,int port,String username,String password) {
		this.host = host;
		this.port= port;
		this.username = username;
		this.password = password;
	}
	
	private Connection(String host,int port,String clientId,String username,String password) {
		this(host,port,username,password);
		this.clientId = clientId;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getClientId() {
		return clientId;
	}
	
	
}
