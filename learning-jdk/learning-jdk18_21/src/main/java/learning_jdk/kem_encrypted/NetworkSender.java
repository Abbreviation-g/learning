package learning_jdk.kem_encrypted;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkSender {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {
            // 第一步，创建sender对象
            Sender sender = new Sender();
            // 第二步，接收公钥
            KeyExchangeResult publicKeyInfo = (KeyExchangeResult) in.readObject();
            // 第三步，假面要发送的消息
            String message = "Hello, 这是使用Kem";
            EncryptedMessage encryptedMessage = sender.encryptMessage(message, publicKeyInfo);
            // 第四步，发送消息
            out.writeObject(encryptedMessage);
            out.flush();
            System.out.println("消息发送完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
