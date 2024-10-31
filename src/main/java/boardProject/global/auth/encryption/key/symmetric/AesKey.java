package boardProject.global.auth.encryption.key.symmetric;

import boardProject.global.auth.encryption.key.EncryptionKeyManager;
import boardProject.global.auth.encryption.key.properties.KeyProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AesKey implements EncryptionKeyManager {

    @Getter
    private SecretKey aesKey;

    private final KeyProperties keyProperties;


    public void generateKey() {
        try {

            log.info("Attempt to create AES key");

            String keyAlgorithm = keyProperties.getAesAlgorithm();
            int keySize = keyProperties.getAesKeySize();

            KeyGenerator keyGenerator = KeyGenerator.getInstance(keyAlgorithm);
            keyGenerator.init(keySize);

            this.aesKey = keyGenerator.generateKey();

            log.info("Success to create AES key");

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

    public void loadKeyDataFromKeyStore(SecretKey keyDataFromKeyStore) {

        log.info("Attempt to load AES key data from KeyStore");
        this.aesKey = keyDataFromKeyStore ;
        log.info("Success to load AES key data from KeyStore");
    }

}