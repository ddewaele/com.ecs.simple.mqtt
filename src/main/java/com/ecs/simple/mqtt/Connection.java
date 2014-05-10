package com.ecs.simple.mqtt;

public class Connection {

	public static final Connection CLOUD_MQTT_CONNECTION = new Connection("m10.cloudmqtt.com",19104,"","");
	public static final Connection TELEMETRY_MQTT_CONNECTION = new Connection("q.m2m.io",1883,"","","");
	public static final Connection MOSQUITTO_TEST_CONNECTION = new Connection("test.mosquitto.org",1883,"","");
	public static final Connection MOSQUITTO_LOCAL_CONNECTION = new Connection("localhost",1883,"","");
	public static final Connection MQTT_DASHBOARD_CONNECTION = new Connection("broker.mqttdashboard.com",1883,"","");
	public static final Connection MQTT_HETZNER_CONNECTION = new Connection("dev-api.fleetprobe.com",1883,"","");
	
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

	public boolean hasUsernamePassword() {
		return !(getUsername() == null || getUsername().trim().equals("") || getPassword() == null || getPassword().trim().equals("") );
	}
	
}
