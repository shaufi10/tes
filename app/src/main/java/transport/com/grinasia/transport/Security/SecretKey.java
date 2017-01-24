package transport.com.grinasia.transport.Security;

import android.util.Base64;

/**
 * Created by coder on 24-Jan-17.
 */

public class SecretKey {
    static {
        System.loadLibrary("GrinasiaS");
    }

    private native static String getSecretKey();

    private static String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        char[] hexData = hex.toCharArray();
        for (int count = 0; count < hexData.length - 1; count += 2) {
            int firstDigit = Character.digit(hexData[count], 16);
            int lastDigit = Character.digit(hexData[count + 1], 16);
            int decimal = firstDigit * 16 + lastDigit;
            sb.append((char)decimal);
        }
        return sb.toString();
    }

    public static String getOriginalSecretKey() throws Exception{
        byte[] secretKey = Base64.decode(getSecretKey(), Base64.DEFAULT);

        String secretKeyInHex = new String(secretKey, "UTF-8");

        return hexToString(secretKeyInHex);
    }
}