package learning_jdk.kem_encrypted;

import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import javax.crypto.Cipher;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Receiver {
    private KeyPair keyPair;
    private String algorithm = "X25519";

    public Receiver() throws Exception{
        generateKeyPair();
        System.out.println("接收方: 密钥对生成完成");
    }

    private void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    public KeyExchangeResult getPublicKeyInfo(){
        return new KeyExchangeResult(algorithm, keyPair.getPublic().getEncoded());
    }

    public String decryptMessage(EncryptedMessage encryptedMessage) throws Exception {
        System.out.println("接收方: 开始解密消息->>>>");

        // 1. kem方式：使用接收方的私钥解封获得共享密钥
        KEM receiverKem = KEM.getInstance("DHKEM");
        KEM.Decapsulator decapsulator = receiverKem.newDecapsulator(keyPair.getPrivate());
        SecretKey sharedSecret = decapsulator.decapsulate(encryptedMessage.getEncapsulation());
        String sharedSecreformatHex = HexFormat.of().formatHex(sharedSecret.getEncoded());
        System.out.println("KEM解封完成，共享密钥：" + sharedSecreformatHex);

        // 2. 将共享密钥转换为AES对称密钥
        SecretKey aesKey = new SecretKeySpec(sharedSecret.getEncoded(), "AES");

        // 3. 使用对称密钥解密实际消息
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, encryptedMessage.getNonce());
        cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);

        // 解密实际消息
        byte[] decryptedText = cipher.doFinal(encryptedMessage.getCiphertext());
        String message = new String(decryptedText);
        System.out.println("消息解密完成");
        
        return message;
    }
}
