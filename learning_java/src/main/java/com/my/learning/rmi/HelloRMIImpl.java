package com.my.learning.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRMIImpl extends UnicastRemoteObject implements IHelloRMI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * i 这里必须是构造器抛出RemoteException
	 * 因为继承父类UnicastRemoteObject构造器中抛出RemoteException
	 * 同时这个类是必须继承UnicastRemoteObject这个类
	 */
	public HelloRMIImpl() throws RemoteException {
	}

	@Override
	public String sayHelloToClient() throws RemoteException {
		return "你好客户端，我是服务端";
	}

	@Override
	public void shutdownEclipse() throws RemoteException {
		System.out.println("shutdownEclipse");
	}

}
