package com.my.learning.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHelloRMI extends Remote {
	
	public String sayHelloToClient()throws RemoteException;
	
	void shutdownEclipse() throws RemoteException;
}
