package transport.com.grinasia.transport.Security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by coder on 24-Jan-17.
 */

public class AES {
        public static byte[] encrypt(byte[] ivBytes, byte[] keyBytes, byte[] textBytes)
                throws Exception {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] key = messageDigest.digest(keyBytes);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key, "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return cipher.doFinal(textBytes);
        }

        public static byte[] decrypt(byte[] ivBytes, byte[] keyBytes, byte[] textBytes)
                throws java.io.UnsupportedEncodingException,
                NoSuchAlgorithmException,
                NoSuchPaddingException,
                InvalidKeyException,
                InvalidAlgorithmParameterException,
                IllegalBlockSizeException,
                BadPaddingException {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] key = messageDigest.digest(keyBytes);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return cipher.doFinal(textBytes);
        }
    }


