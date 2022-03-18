import javax.crypto.*;
import java.io.*;
import java.util.Base64;

public class Encrypt {
    public Encrypt(Cipher cipher, SecretKey key) {
        this.cipher = cipher;
        this.key = key;
    }

    private final Cipher cipher;
    private final SecretKey key;

    public void encryptFile(File file) {
        StringBuffer buffer = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
            String line = reader.readLine();
            String encryptedLine;

            while (line != null) {
                encryptedLine = encrypt(line, key);
                buffer.append(encryptedLine).append("\n");

                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decryptFile(File file) {
        StringBuffer buffer = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
            String line = reader.readLine();
            String decryptedLine;

            while (line != null) {
                decryptedLine = decrypt(line, key);
                buffer.append(decryptedLine).append("\n");

                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plainText, SecretKey secretKey) throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(encryptedByte);
    }

    public String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        return new String(decryptedByte);
    }

    public void encryptDirectory(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    encryptDirectory(f);
                }
                else {
                    encryptFile(f);
                }
            }
        }
    }

    public void decryptDirectory(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    decryptDirectory(f);
                }
                else {
                    decryptFile(f);
                }
            }
        }
    }
}

