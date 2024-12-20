package com.my.learning.rpc;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class Server {
	private static final int port = 9091;

	public static void main(String[] args) throws Exception {
		WebServer webServer = new WebServer(port);

		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.load(Thread.currentThread().getContextClassLoader(), "MyHandlers.properties");
		xmlRpcServer.setHandlerMapping(phm);
		XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		serverConfig.setEnabledForExtensions(true);
		serverConfig.setContentLengthOptional(false);

		webServer.start();
	}

}