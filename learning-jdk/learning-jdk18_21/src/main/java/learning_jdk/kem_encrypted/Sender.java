package learning_jdk.kem_encrypted;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HexFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KEM;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Sender {
    private String algorithm = "X25519";

    public EncryptedMessage encryptMessage(String message, KeyExchangeResult publicKeyInfo)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("发送方：开始加密消息->");
        // 1,创建公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(publicKeyInfo.getAlgorithm());
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyInfo.getPublicKey()));
        System.out.println("公钥加载完成");

        // 2,KEM方式：使用接收方的公钥，通过一个更安全更结构化的流程，生成并封装一个对称密钥
        KEM senderKem = KEM.getInstance("DHKEM");
        KEM.Encapsulator encapsulator = senderKem.newEncapsulator(publicKey);
        KEM.Encapsulated encapsulated = encapsulator.encapsulate();
        SecretKey sharedSecret = encapsulated.key();
        String sharedSecreformatHex = HexFormat.of().formatHex(sharedSecret.getEncoded());
        System.out.println("KEM封装完成， 共享密钥: "+sharedSecreformatHex);

        // 3. 将共享密钥转换为AES密钥（AES是目前最流行的）
        SecretKey aesKey = new SecretKeySpec(sharedSecret.getEncoded(), "AES");

        // 4. 获取一个AES/GCM模式的加密器实例，其中GCM提供加密认证， NoPadding表示不适用填充，该加密器后续用于加密消息内容
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        // 生成随机数
        byte[] nonce = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(nonce);
        // 使用堆成密钥aeskey初始化cipher
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(128, nonce));
        byte[] cipherText = cipher.doFinal(message.getBytes());
        System.out.println("消息加密完成");
        
        return new EncryptedMessage(encapsulated.encapsulation(), cipherText, nonce);
    }
}
