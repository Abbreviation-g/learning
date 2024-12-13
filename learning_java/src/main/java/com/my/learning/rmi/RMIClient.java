package com.my.learning.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {
	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        /**
         * IHelloRMI接口从服务端拷贝过来即可
         * 这里必须注意的是IHelloRMI的包路径必须与服务端一致
         * 如果不一致，会发生ClassNotFoundException
         * Naming.lookup是在指定地址插在rmi服务
         */
        IHelloRMI helloRMI = (IHelloRMI) Naming.lookup("//localhost:8889/HelloRMI");
        String s = helloRMI.sayHelloToClient();
        System.out.println(s);
    }
}
