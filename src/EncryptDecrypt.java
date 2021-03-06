import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
    This class is responsible of:
    A) Encrypt string data passed by the caller and return encrypted String data 
    B) Encrypt string data passed by the caller and return decrypted String data
*/
public class EncryptDecrypt {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
public String encrypt(String strToEncrypt,String strKey) throws Exception{

	try {
        setKey(strKey);
		Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));	
	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e);
	}
    }

    public String decrypt(String strToDecrypted,String strKey) throws Exception{
        try {
            setKey(strKey);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypted)));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static void setKey(final String myKey) throws Exception {
        MessageDigest sha = null;
        try {
          key = myKey.getBytes("UTF-8");
          sha = MessageDigest.getInstance("SHA-1");
          key = sha.digest(key);
          key = Arrays.copyOf(key, 16);
          secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new Exception(e);
        }
      }
    
}
