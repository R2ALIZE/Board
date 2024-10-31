package boardProject.global.auth.encryption.strategy;


import org.springframework.stereotype.Component;

@Component
public interface EncryptionStrategy {
        String encrypt(String plainText);
        String decrypt(String encryptedText);

}
