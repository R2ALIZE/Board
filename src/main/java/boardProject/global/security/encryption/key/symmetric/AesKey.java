package boardProject.global.security.encryption.key.symmetric;

import boardProject.global.security.encryption.key.EncryptionKeyManager;
import boardProject.global.security.encryption.key.properties.KeyProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
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
public class AesKey implements EncryptionKeyManager {

    @Getter
    private SecretKey aesKey;

    private boolean isLoaded = false;

    private final KeyProperties keyProperties;


    public void generateKey() {
        try {

            log.info(ENCRYPTION,"Attempt to create AES key");

            String keyAlgorithm = keyProperties.getAesAlgorithm();
            int keySize = keyProperties.getAesKeySize();

            KeyGenerator keyGenerator = KeyGenerator.getInstance(keyAlgorithm);
            keyGenerator.init(keySize);

            log.info(ENCRYPTION,"Attempt to load newly created AES key to JVM memory");
            this.aesKey = keyGenerator.generateKey();
            isLoaded = true;
            log.info(ENCRYPTION,"Success to load newly created AES key to JVM memory");

            log.info(ENCRYPTION,"Success to create AES key");

        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException(StatusCode.KEY_GENERATE_FAIL);
        }
    }


    public String getKeyAlgorithm() {
        return keyProperties.getAesAlgorithm();
    }

    public int getKeySize() {
        return keyProperties.getAesKeySize();
    }

    public String getKeyAlias() {
        return keyProperties.getAesKeyAlias();
    }

    public char[] getKeyPassword() {
        return keyProperties.getAesKeyPassword();
    }


    public boolean isKeyLoaded() {
        return aesKey != null;
    }

    public void loadKeyDataFromKeyStore(SecretKey keyDataFromKeyStore) {


        log.info(ENCRYPTION,"Attempt to load AES key data from KeyStore");
        this.aesKey = keyDataFromKeyStore ;
        log.info(ENCRYPTION,"Success to load AES key data from KeyStore");
    }

}