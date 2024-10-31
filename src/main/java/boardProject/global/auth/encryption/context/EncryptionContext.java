package boardProject.global.auth.encryption.context;

import boardProject.global.auth.encryption.strategy.EncryptionStrategy;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Setter
public class EncryptionContext {

    private EncryptionStrategy encryptionStrategy;

    public EncryptionContext(EncryptionStrategy encryptionStrategy) {
        this.encryptionStrategy = encryptionStrategy;
    }

    public String encrypt(String plainText)
            throws GeneralSecurityException, UnsupportedEncodingException {

        if (encryptionStrategy == null) {
            throw new BusinessLogicException(StatusCode.ENCRYPTION_STRATEGY_NOT_SET);
        }

        return encryptionStrategy.encrypt(plainText);
    }

    public String decrypt(String encryptedText)
            throws GeneralSecurityException, UnsupportedEncodingException {

        if (encryptionStrategy == null) {
            throw new BusinessLogicException(StatusCode.ENCRYPTION_STRATEGY_NOT_SET);
        }

        return encryptionStrategy.decrypt(encryptedText);
    }


}
