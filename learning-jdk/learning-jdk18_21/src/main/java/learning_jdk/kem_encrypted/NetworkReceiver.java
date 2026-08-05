package learning_jdk.kem_encrypted;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkReceiver {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while(true) {
                try (
                    Socket clientSocket = serverSocket.accept();
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());){
                        // 第一步，接收方生成非对称密钥对
                        // 创建receiver
                        Receiver receiver = new Receiver();
                        // 第二部发送公钥给客户端
                        out.writeObject(receiver.getPublicKeyInfo());
                        out.flush();
                        // 第三步：接收加密信息
                        EncryptedMessage encryptedMessage = (EncryptedMessage) in.readObject();
                        // 第四步：解密
                        String decryptedMessage = receiver.decryptMessage(encryptedMessage);
                        System.out.println("接收到消息: "+ decryptedMessage);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
