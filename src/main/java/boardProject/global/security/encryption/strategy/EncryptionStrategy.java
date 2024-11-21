package boardProject.global.security.encryption.strategy;


import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Component
public interface EncryptionStrategy {
        String encrypt(String plainText) throws GeneralSecurityException, UnsupportedEncodingException;
        String decrypt(String encryptedText) throws GeneralSecurityException, UnsupportedEncodingException;

}
