package boardProject.global.auth.encryption.key.hmac;

import boardProject.global.auth.encryption.key.EncryptionKeyManager;
import boardProject.global.auth.encryption.key.properties.KeyProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class HmacKey implements EncryptionKeyManager {

    @Getter
    private SecretKey hmacKey;

    private final KeyProperties keyProperties;


    @Override
    public void generateKey() {
        try {

           String algorithm =  keyProperties.getHmacKeyAlgorithm();

           KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

           this.hmacKey = keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException(StatusCode.KEY_GENERATE_FAIL);
        }
    }

    @Override
    public String getKeyAlgorithm() {
       return keyProperties.getHmacKeyAlgorithm();
    }

    @Override
    public int getKeySize() {
        return 0;
    }

    @Override
    public String getKeyAlias() {
        return keyProperties.getHmacKeyAlias();
    }

    @Override
    public char[] getKeyPassword() {
        return keyProperties.getHmacKeyPassword();
    }

    @Override
    public boolean isKeyLoaded() {
        return hmacKey != null;
    }

    public void loadKeyDataFromKeyStore(SecretKey keyDataFromKeyStore) {
        this.hmacKey = keyDataFromKeyStore ;
    }

}