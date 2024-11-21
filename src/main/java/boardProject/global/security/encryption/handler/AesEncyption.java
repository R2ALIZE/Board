package boardProject.global.security.encryption.handler;

import boardProject.global.security.encryption.key.symmetric.AesKey;
import boardProject.global.security.encryption.strategy.EncryptionStrategy;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.RequiredArgsConstructor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@RequiredArgsConstructor
public class AesEncyption implements EncryptionStrategy {


    private static final int IV_SIZE = 16;

    private final AesKey aesKey;


    public byte[] generateIv() {

        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);

        return iv;
    }

    /** AES-CBC **/

    @Override
    public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

        SecretKey secretKey = aesKey.getAesKey();

        if (secretKey == null) {
            throw new BusinessLogicException(StatusCode.KEY_NOT_LOADED);
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");


            IvParameterSpec ivSpec = new IvParameterSpec(generateIv());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);


        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedText) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {

        SecretKey secretKey = aesKey.getAesKey();

        if (secretKey == null) {
            throw new BusinessLogicException(StatusCode.KEY_NOT_LOADED);
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec ivSpec = new IvParameterSpec(generateIv());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);


        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, "UTF-8");
    }

}
