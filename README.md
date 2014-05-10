#Introduction

This is a simple project for experimenting with MQTT
(work in progress).

# Mosquitto

## Installing Mosquitto 

### CentOS

Preparing the environment 

	cd /etc/yum/yum.repos.d
	wget http://download.opensuse.org/repositories/home:/oojah:/mqtt/CentOS_CentO...
	sudo yum update

Installing mosquitto. The available packages are: 

- mosquitto
- mosquitto-clients
- libmosquitto1
- libmosquitto-devel
- libmosquittopp1
- libmosquittopp-devel
- python-mosquitto.

	sudo yum install mosquitto

As of writing, no init.d script exists for the CentOS distribution of mosquitto. 
However, it is a simple enough matter to set it running as a daemon, you'll just need to restart it yourself whenever your machine gets restarted.

	sudo su
	/usr/sbin/mosquitto -d -c /etc/mosquitto/mosquitto.conf > /var/log/mosquitto.log 2>&1

### Mac OS X
Installing mosquitto on MAac OS X is pretty eas using brew.

	brew install mosquitto
    Warning: Your Xcode (4.2) is outdated
    Please update to Xcode 4.6.3.
    Xcode can be updated from the App Store.
    Warning: It appears you have MacPorts or Fink installed.
    Software installed with other package managers causes known problems for
    Homebrew. If a formula fails to build, uninstall MacPorts/Fink and try again.
    ==> Installing dependencies for mosquitto: c-ares, openssl
    ==> Installing mosquitto dependency: c-ares
    ==> Downloading http://c-ares.haxx.se/download/c-ares-1.10.0.tar.gz
    ######################################################################## 100.0%
    ==> ./configure --prefix=/usr/local/Cellar/c-ares/1.10.0
    ==> make install
     /usr/local/Cellar/c-ares/1.10.0: 57 files, 536K, built in 84 seconds
    ==> Installing mosquitto dependency: openssl
    ==> Downloading https://downloads.sf.net/project/machomebrew/Bottles/openssl-1.0.1g.lion.bottle.tar.gz
    ######################################################################## 100.0%
    ==> Pouring openssl-1.0.1g.lion.bottle.tar.gz
    ==> Caveats
    A CA file has been bootstrapped using certificates from the system
    keychain. To add additional certificates, place .pem files in
      /usr/local/etc/openssl/certs
    
    and run
      /usr/local/opt/openssl/bin/c_rehash
    
    This formula is keg-only, so it was not symlinked into /usr/local.
    
    Mac OS X already provides this software and installing another version in
    parallel can cause all kinds of trouble.
    
    The OpenSSL provided by OS X is too old for some software.
    
    Generally there are no consequences of this for you. If you build your
    own software and it requires this formula, you'll need to add to your
    build variables:
    
        LDFLAGS:  -L/usr/local/opt/openssl/lib
        CPPFLAGS: -I/usr/local/opt/openssl/include
    
    ==> Summary
     /usr/local/Cellar/openssl/1.0.1g: 429 files, 15M
    ==> Installing mosquitto
    ==> Downloading https://downloads.sf.net/project/machomebrew/Bottles/mosquitto-1.3.1_1.lion.bottle.tar.gz
    ######################################################################## 100.0%
    ==> Pouring mosquitto-1.3.1_1.lion.bottle.tar.gz
    ==> Caveats
    mosquitto has been installed with a default configuration file.
    You can make changes to the configuration by editing:
        /usr/local/etc/mosquitto/mosquitto.conf
    
    Python client bindings can be installed from the Python Package Index:
        pip install mosquitto
    
    Javascript client has been removed, see Eclipse Paho for an alternative.
    
    To have launchd start mosquitto at login:
        ln -sfv /usr/local/opt/mosquitto/*.plist ~/Library/LaunchAgents
    Then to load mosquitto now:
        launchctl load ~/Library/LaunchAgents/homebrew.mxcl.mosquitto.plist
    Or, if you don't want/need launchctl, you can just run:
        mosquitto -c /usr/local/etc/mosquitto/mosquitto.conf

    ==> Summary
      /usr/local/Cellar/mosquitto/1.3.1_1: 26 files, 640K
    
After  Mosquitto has been installed you can start it like this:

    /usr/local/sbin/mosquitto -c /usr/local/etc/mosquitto/mosquitto.conf
    
    /root/HiveMQ/hivemq-1.5.0/bin/run.sh > /var/log/hivemq.log 2>&1
    
brew install openssl


- http://stackoverflow.com/questions/21508866/openssl-rvm-brew-conflicting-error

## Tesing your broker

There are several ways to test your broker
	
### Using mosquitto_pub / mosquitto_sub 
	
#### Testing the publisher
	
	export mqtt_host=localhost
	export mqtt_port=1883
	i=0 ; 
	while [ $i -lt 5 ] ; do 
	   mosquitto_pub -h $mqtt_host -t vdm/123/engineOff --qos 2 -m "123|456|789|000|$(date +"%m-%d-%Y-%H:%M:%S")" 
	   sleep 60 ; 
	   i=$(( $i + 1 )) ; 
	done

#### Testing the subscriber.

	mosquitto_sub -h localhost -t vdm/+/engineOff

# Thoughts...

some topics to write about

## Protocol overhead is very small

Protocol overhead is very small. To deliver a message the following is needed

- 2 bytes
- a topic (subject)
- the payload 

So combining a low overhead protocol with a payload that is compressed will help on lines that are low bandwidth.  
A good practise on constrained networks is to ensure the payload is kept as small as possible. 

## Quality levels

Talk about the different quality levels.


	tcpdump -v -s 0 -A tcp port 1883

###QoS Level 0 : At most once

Here we simply publish a message once but don't care if we get a response.

	MQTTFrame { type: CONNECT, qos: AT_MOST_ONCE, dup:false }
	MQTTFrame { type: CONNACK, qos: AT_MOST_ONCE, dup:false }
	MQTTFrame { type: PUBLISH, qos: AT_MOST_ONCE, dup:false }

###QoS Level 1 : At least once

Here we publish a message once but don't care if we get a respnose.


MQTTFrame { type: CONNECT, qos: AT_MOST_ONCE, dup:false }
MQTTFrame { type: CONNACK, qos: AT_MOST_ONCE, dup:false }
MQTTFrame { type: PUBLISH, qos: AT_LEAST_ONCE, dup:false }
MQTTFrame { type: PUBACK, qos: AT_MOST_ONCE, dup:false }


###QoS Level 2 : Exactly once

MQTTFrame { type: CONNECT, qos: AT_MOST_ONCE, dup:false }
MQTTFrame { type: CONNACK, qos: AT_MOST_ONCE, dup:false }
MQTTFrame { type: PUBLISH, qos: EXACTLY_ONCE, dup:false }
MQTTFrame { type: PUBREC, qos: AT_MOST_ONCE, dup:false }
MQTTFrame { type: PUBREL, qos: AT_LEAST_ONCE, dup:false }
MQTTFrame { type: PUBCOMP, qos: AT_MOST_ONCE, dup:false }


## Errors encountered

	java.io.IOException: An existing connection was forcibly closed by the remote host
		at sun.nio.ch.SocketDispatcher.read0(Native Method) ~[na:1.6.0_45]
		at sun.nio.ch.SocketDispatcher.read(Unknown Source) ~[na:1.6.0_45]
		at sun.nio.ch.IOUtil.readIntoNativeBuffer(Unknown Source) ~[na:1.6.0_45]
		at sun.nio.ch.IOUtil.read(Unknown Source) ~[na:1.6.0_45]
		at sun.nio.ch.SocketChannelImpl.read(Unknown Source) ~[na:1.6.0_45]
		at org.fusesource.hawtdispatch.transport.AbstractProtocolCodec.read(AbstractProtocolCodec.java:326) ~[hawtdispatch-transport-1.20.jar:1.20]
		at org.fusesource.hawtdispatch.transport.TcpTransport.drainInbound(TcpTransport.java:706) ~[hawtdispatch-transport-1.20.jar:1.20]
		at org.fusesource.hawtdispatch.transport.TcpTransport$6.run(TcpTransport.java:588) [hawtdispatch-transport-1.20.jar:1.20]
		at org.fusesource.hawtdispatch.internal.NioDispatchSource$3.run(NioDispatchSource.java:209) [hawtdispatch-1.20.jar:1.20]
		at org.fusesource.hawtdispatch.internal.SerialDispatchQueue.run(SerialDispatchQueue.java:100) [hawtdispatch-1.20.jar:1.20]
		at org.fusesource.hawtdispatch.internal.pool.SimpleThread.run(SimpleThread.java:77) [hawtdispatch-1.20.jar:1.20]


	Exception in thread "main" java.io.IOException: Could not connect: CONNECTION_REFUSED_NOT_AUTHORIZED
		at org.fusesource.mqtt.client.CallbackConnection$LoginHandler$1.onTransportCommand(CallbackConnection.java:331)
		at org.fusesource.hawtdispatch.transport.TcpTransport.drainInbound(TcpTransport.java:709)
		at org.fusesource.hawtdispatch.transport.TcpTransport$6.run(TcpTransport.java:588)
		at org.fusesource.hawtdispatch.internal.NioDispatchSource$3.run(NioDispatchSource.java:209)
		at org.fusesource.hawtdispatch.internal.SerialDispatchQueue.run(SerialDispatchQueue.java:100)
		at org.fusesource.hawtdispatch.internal.pool.SimpleThread.run(SimpleThread.java:77)





#Public brokers

## MQTT Dashboard

broker.mqttdashboard.com
1883


## CloudMQTT

# References

## The specification

- http://public.dhe.ibm.com/software/dw/webservices/ws-mqtt/mqtt-v3r1.html

## Online brokers

- http://www.cloudmqtt.com/
- http://test.mosquitto.org/
- http://broker.mqttdashboard.com

- http://www.hivemq.com/
- http://www.banym.de/linux/try-the-hivemq-mqtt-broker-on-centos-6-x

- https://customer.cloudmqtt.com/customer/
- https://app.thingfabric.com/dashboard
- http://www.hivemq.com/demos/websocket-client/

- http://www.hivemq.com/full-featured-mqtt-client-browser/
- https://github.com/hivemq/hivemq-mqtt-web-client

## Client libraries

- https://github.com/eclipse/paho.mqtt.java
- https://github.com/fusesource/mqtt-client

- https://2lemetry.atlassian.net/wiki/display/KB/2lemetry+Knowledge+Base
- https://2lemetry.zendesk.com/hc/en-us/articles/200043648-How-does-a-user-connect-to-2lemetry-s-Platform-


- http://www.banym.de/linux/try-the-hivemq-mqtt-broker-on-centos-6-x

## Design

- http://www.infoq.com/articles/practical-mqtt-with-paho
- https://groups.google.com/forum/#!topic/mqtt/cyy0fiHFV4w
- http://www.ekito.fr/people/?p=4375
- http://mobilebit.wordpress.com/2014/01/10/cesv2v/
- http://dejanglozic.com/2014/05/06/rest-and-mqtt-yin-and-yang-of-micro-service-apis/?utm_content=buffer7b69a&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer
- http://www.disk91.com/2013/technology/networks/mqtt-compared-to-html-for-its-network-saving/

## Websockets

- http://mqtt.org/wiki/doku.php/mqtt_over_websockets