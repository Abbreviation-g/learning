package learning_jdk.kem_encrypted;

import java.io.Serializable;

public class EncryptedMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    // 密钥封装数据(包含共享密钥信息)
    private final byte[] encapsulation; 
    private final byte[] ciphertext; // 密文数据
    private final byte[] nonce; // 随机数，用于加密算法的初始化向量

    public EncryptedMessage(byte[] encapsulation, byte[] ciphertext, byte[] nonce) {
        this.encapsulation = encapsulation != null ? encapsulation.clone() : null;
        this.ciphertext = ciphertext != null ? ciphertext.clone() : null;
        this.nonce = nonce != null ? nonce.clone() : null;
    }

    public byte[] getCiphertext() {
        return ciphertext != null ? ciphertext.clone() : null;
    }

    public byte[] getEncapsulation() {
        return encapsulation != null ? encapsulation.clone() : null;
    }

    public byte[] getNonce() {
        return nonce != null ? nonce.clone() : null;
    }

    @Override
    public String toString() {
        return String.format("EncryptedMessage{encapsulation=%s, ciphertext=%s, nonce=%s}", 
                encapsulation != null ? encapsulation.length : 0,
                ciphertext != null ? ciphertext.length : 0,
                nonce != null ? nonce.length : 0);
    }
}
