package miscellaneous;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;


public class Aes {
    /*public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        System.out.println(generateKey());
    }*/
    private static final String SALT = "ssshhhhhhhhhhh!!!!";
    private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho";


    public int generateKey(){
        Random rg = new Random();
        int rand = rg.nextInt(3);
        int key = 0;
        if (rand == 0) {
            key = 128;
        } else if (rand == 1) {
            key = 192;
        } else if (rand == 2) {
            key = 256;
        }
        return key;
    }
    public  String encrypt(String message, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //create byte
        byte[] mi = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        //creates a length of 16
        IvParameterSpec ivspec = new IvParameterSpec(mi);
        //create factory
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        //creates keysec
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, keySize);
        //generates keyspec
        SecretKey tmp = factory.generateSecret(spec);
        //generate secret key for AES
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        //create cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //initialise ciphern and encrypt
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        //return string
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }
    public String decrypt(String message, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //create byte
        byte[] mi = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        //creates a length of 16
        IvParameterSpec ivspec = new IvParameterSpec(mi);
        //creates factory
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        //creates keyspec
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, keySize);
        //generates keyspec
        SecretKey tmp = factory.generateSecret(spec);
        //generates secret Key for AES
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        //creates cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        //initialise cipher and decrypt
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        //return string
        return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
    }
}
