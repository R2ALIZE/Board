package boardProject.global.security.encryption.key.hmac;

import boardProject.global.security.encryption.key.EncryptionKeyManager;
import boardProject.global.security.encryption.key.properties.KeyProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static boardProject.global.logging.LogMarkerFactory.ENCRYPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class HmacKey implements EncryptionKeyManager {

    @Getter
    private SecretKey hmacKey;

    private final KeyProperties keyProperties;


    @Override
    public void generateKey() throws NoSuchAlgorithmException {

          log.info(ENCRYPTION,"Attempt to create hmac secretkey");

           String algorithm =  keyProperties.getHmacKeyAlgorithm();
           KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
           SecretKey secretKey = keyGenerator.generateKey();

         log.info(ENCRYPTION,"Success to create hmac secretkey");

         log.info(ENCRYPTION,"Attempt to load newly created hmac secretkey to JVM memory");

           this.hmacKey = secretKey;

         log.info(ENCRYPTION,"Attempt to load newly created hmac secretkey to JVM memory");

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