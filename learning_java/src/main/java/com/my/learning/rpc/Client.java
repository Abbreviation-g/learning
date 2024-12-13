package com.my.learning.rpc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Client {
    public static void main(String[] args) throws IOException {
        try {

            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://127.0.0.1:9091"));

            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);

            Object[] params = new Object[]{new Integer(31), new Integer(9)};
            Integer result = (Integer) client.execute("MyCalculator.myAdd", params);
            System.out.println(result);

        } catch (XmlRpcException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
