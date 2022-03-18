import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        //Key wird generiert
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // block size is 128bits
        SecretKey secretKey = keyGenerator.generateKey();

        //Chiphrierungsstandard wird gesetzt
        Cipher cipher = Cipher.getInstance("AES");

        Encrypt encrypt = new Encrypt(cipher, secretKey);

        encrypt.encryptDirectory(new File("./res"));
    }
}
