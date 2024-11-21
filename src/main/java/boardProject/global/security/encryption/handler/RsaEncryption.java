package boardProject.global.security.encryption.handler;

import boardProject.global.security.encryption.key.asymmetric.RsaKey;
import boardProject.global.security.encryption.strategy.EncryptionStrategy;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


@RequiredArgsConstructor
public class RsaEncryption implements EncryptionStrategy {

    private final RsaKey rsaKey;

    @Override
    public String encrypt(String plainText)
            throws GeneralSecurityException {

        PublicKey publicKey = rsaKey.getPublicKey();

        if (publicKey == null) {
            throw new BusinessLogicException(StatusCode.KEY_NOT_LOADED);
        }


        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE,publicKey);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }



    @Override
    public String decrypt(String EncryptedText)
            throws GeneralSecurityException {

        PrivateKey privateKey = rsaKey.getPrivateKey();

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(EncryptedText));

        return new String(decryptedBytes);

    }


}
