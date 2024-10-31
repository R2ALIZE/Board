package boardProject.global.auth.encryption.context;

import boardProject.global.auth.encryption.strategy.EncryptionStrategy;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
public class EncryptionContext {

    private EncryptionStrategy encryptionStrategy;


    public String encrypt(String plainText) {
        return encryptionStrategy.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return encryptionStrategy.decrypt(encryptedText);
    }


}
