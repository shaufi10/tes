package transport.com.grinasia.transport.Utils;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import transport.com.grinasia.transport.Config.BaseConfig;

/**
 * Created by coder on 24-Jan-17.
 */
public class Strings {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (byte aB : b) {
            result +=
                    Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static String AsciitoString(String text){
        String decodedString = "";
        String[] arrayText = text.split("/");
        for (String anArrayText : arrayText) {
            int asciiCode = Integer.parseInt(anArrayText);
            decodedString += Character.toString((char) asciiCode);
        }
        return decodedString;
    }

    public static String toASCIICode(String text){
        String ascii_code = "";
        for (int indexWord = 0; indexWord < text.length(); indexWord++){
            char character = text.charAt(indexWord);
            int int_ascii = (int)character;
            if (indexWord != text.length() - 1){
                ascii_code = ascii_code + String.valueOf(int_ascii) + "/";
            } else {
                ascii_code = ascii_code + String.valueOf(int_ascii);
            }
        }
        return generateSalt() + "|" + Base64.encodeToString(ascii_code.getBytes(), Base64.DEFAULT).replaceAll("(\\r|\\n)", "");
    }

    private static String generateSalt() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (new Random().nextFloat() * (rightLimit - leftLimit));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public static String normalizeData(String data){
        return data.replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "");
    }

    public static String extractDataFromParanteheses(String word){
        return word.substring(word.indexOf("(")+1, word.indexOf(")"));
    }

    public static JSONArray sortingFlightClassPrice(JSONArray data) throws Exception{
        JSONArray results = new JSONArray();

        List<JSONObject> flightClassList = new ArrayList<>();

        for (int indexData = 0; indexData < data.length(); indexData++){
            flightClassList.add(data.getJSONObject(indexData));
        }

        for (Iterator<JSONObject> iterator = flightClassList.iterator(); iterator.hasNext();){
            String fareNormalized = Strings.normalizeData(iterator.next().getString("fare"));
            if (fareNormalized.equals("0.0")){
                iterator.remove();
            }
        }

        Iterator flightClassIterator = flightClassList.iterator();

        while (flightClassIterator.hasNext()){
            results.put(flightClassIterator.next());
        }

        return results;
    }

    public static String generateKeySignature(String invoiceCode, String amount, String expirationTime) {
        String param1 = BaseConfig.getMerchantCode() + "%" + BaseConfig.getMerchantPass() + "%" + invoiceCode +
                "%" + amount + "%" + expirationTime;

        String param1UCase = param1.toUpperCase();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(param1UCase.getBytes("UTF-8"));
            byte[] signatureDigest = messageDigest.digest();
            String hashSignature = encodeHex(signatureDigest);
            return hashSignature.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String encodeHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
