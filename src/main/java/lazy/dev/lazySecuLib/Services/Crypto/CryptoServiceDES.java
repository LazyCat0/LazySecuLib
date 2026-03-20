package lazy.dev.lazySecuLib.Services.Crypto;

/*
    This is a class for easier encrypting and decrypting.
    If you want to use other algorithm — Check other classes in Crypto package
*/
//! NOT RECOMMENDED TO USE
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoServiceDES {
    private static final String ALGO = "DES";
    private SecretKey key;

    public CryptoServiceDES(byte[] keyBytes) {
        this.key = new SecretKeySpec(keyBytes, ALGO);
    }

    public String encrypt(String s) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(s.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String s) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = cipher.doFinal();

        return new String(cipher.doFinal(decrypted));
    }
}
