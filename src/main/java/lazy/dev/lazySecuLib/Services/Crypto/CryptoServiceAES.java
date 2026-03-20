package lazy.dev.lazySecuLib.Services.Crypto;

/*
    This is a class for easier encrypting and decrypting.
    If you want use other algorithm — Check other classes in Crypto package
*/

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.util.Base64;

public class CryptoServiceAES {
    private static final String ALGO = "AES/GCM/NoPadding";
    private final SecretKeySpec key;

    public CryptoServiceAES(byte[] keyBytes) {
        this.key = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        byte[] combined = ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedBase64) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedBase64);

        ByteBuffer bb = ByteBuffer.wrap(combined);
        byte[] iv = new byte[12];
        bb.get(iv);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        return new String(cipher.doFinal(cipherText));
    }
}
