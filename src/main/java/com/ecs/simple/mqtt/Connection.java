package com.ecs.simple.mqtt;

public class Connection {

	
	private static final String HOSTNAME = "m10.cloudmqtt.com";
	private static final int PORT = 19104;
	
	private static final String USERNAME = "";
	private static final String PASSWD = "";

	public static final Connection CLOUD_MQTT_CONNECTION = new Connection(HOSTNAME,PORT,USERNAME,PASSWD);
	
	public static final Connection MOSQUITTO_TEST_CONNECTION = new Connection("test.mosquitto.org",1883,"user","pass");
	
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
