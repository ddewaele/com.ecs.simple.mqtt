#Introduction

This is a simple project for experimenting with MQTT
(work in progress).

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

#Public brokers

## CloudMQTT

# References

- http://public.dhe.ibm.com/software/dw/webservices/ws-mqtt/mqtt-v3r1.html
- http://www.cloudmqtt.com/
- https://customer.cloudmqtt.com/customer/
- https://app.thingfabric.com/dashboard