package learning_jdk.kem_encrypted;

import java.io.Serializable;

public class KeyExchangeResult implements Serializable{
    private static final long serialVersionUID = 1L;

    private final String algorithm; // 密钥封装算法名称 
    private final byte[] publicKey; // 公钥

    public KeyExchangeResult(String algorithm, byte[] publicKey) {
        this.algorithm = algorithm;
        this.publicKey = publicKey != null ? publicKey.clone() : null;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public byte[] getPublicKey() {
        return publicKey != null ? publicKey.clone() : null;
    }

    @Override
    public String toString() {
        return String.format("KeyExchangeResult{algorithm=%s, publicKey=%s}", 
                algorithm,
                publicKey != null ? publicKey.length : 0);
    }
}
