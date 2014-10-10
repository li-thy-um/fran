package helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeHelper {
    public static String encrypt(String info){
        if( info == null ) info = "";
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] srcBytes = info.getBytes();
        md5.update(srcBytes);
        byte[] resultBytes = md5.digest();
        StringBuilder sb = new StringBuilder();
        for( byte b : resultBytes )
            sb.append((char)b);
        return sb.toString();
    }

	public static String randomPsw() {
		return "000000";
	}
}
