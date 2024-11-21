package boardProject.global.security.encryption.keyLoader;

import boardProject.global.security.encryption.key.factory.EncryptionKeyFactory;
import boardProject.global.security.encryption.key.properties.KeyProperties;
import boardProject.global.security.encryption.keyStore.KeyStoreFactory;
import boardProject.global.security.encryption.keyStore.KeyStoreManager;
import boardProject.global.security.encryption.keyStore.properties.KeyStoreProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import static boardProject.global.logging.LogMarkerFactory.APP_INIT;
import static boardProject.global.logging.LogMarkerFactory.ENCRYPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyStoreInitializer {


    private final KeyStoreFactory keyStoreFactory;

    private final KeyStoreProperties keyStoreProperties;

    private final EncryptionKeyFactory keyFactory;

    private final KeyProperties keyProperties;

    private String[] keyStoreTypes = new String[]{"JCEKS","PKCS12"};

    List<KeyStoreManager> loadedKeyStore = new ArrayList<>();


    @PostConstruct
    public void init() throws Exception {
        log.info(APP_INIT,"Initializing Encryption Environment");

        setupKeyStore();
        setupKey(loadedKeyStore);

        log.info(APP_INIT,"Success to Initializing Encryption Environment");
    }



    /** JKS, PKCS12 keyStore 로드 **/
    public void setupKeyStore()
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {

        log.info(ENCRYPTION,"Setup KeyStores");

        for (String keyStoreType : keyStoreTypes) {

                KeyStoreManager selectedTypeOfKeyStore =
                keyStoreFactory.selectKeyStore(keyStoreType.toUpperCase());


            if (selectedTypeOfKeyStore.isKeyStoreExist()) {

                log.info(ENCRYPTION,"{} keystore exist as File",keyStoreType);

                selectedTypeOfKeyStore.loadFromFile();

                loadedKeyStore.add(selectedTypeOfKeyStore);

            } else {

                log.info(ENCRYPTION,"{} keystore not exist as File",keyStoreType);

                selectedTypeOfKeyStore.createKeyStore();
                selectedTypeOfKeyStore.saveToFile();


                loadedKeyStore.add(selectedTypeOfKeyStore);

            }
        }

        log.info(APP_INIT,"Success to Setup Keystores");

    }


    /** AES, RSA, HMAC 키 로드 **/
    public void setupKey(List<KeyStoreManager> loadedKeyStores)
            throws Exception {


        log.info(ENCRYPTION,"Setup SecretKeys");

        if (loadedKeyStores == null) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }



        for (var loadedKeyStore : loadedKeyStores) {

            switch (loadedKeyStore.getKeyStoreType()) {

                case "JCEKS" -> {

                    if (!loadedKeyStore.isKeyExistInKeyStore("aesKey")) {

                        log.info(ENCRYPTION,"AES secretkey not exists in JCEKS keystore");

                        var selectedTypeOfKey = keyFactory.selectKey("AES");

                        selectedTypeOfKey.generateKey();

                        loadedKeyStore.saveKey(selectedTypeOfKey);

                        loadedKeyStore.saveToFile();


                    } else {

                        log.info(ENCRYPTION,"AES secretkey exists in JCEKS keystore");

                        var selectedTypeOfKey = keyFactory.selectKey("AES");
                        loadedKeyStore.loadKey(selectedTypeOfKey);
                    }
                }


                case "PKCS12" -> {

                    if (!loadedKeyStore.isKeyExistInKeyStore("rsaKey")) {


                        log.info(ENCRYPTION,"RSA private key not exists in PKCS12 keystore");

                        var selectedTypeOfKey = keyFactory.selectKey("RSA");

                        selectedTypeOfKey.generateKey();

                        loadedKeyStore.saveKey(selectedTypeOfKey);


                        loadedKeyStore.saveToFile();

                    } else {
                        log.info(ENCRYPTION,"RSA private key exists in PKCS12 keystore");
                        var selectedTypeOfKey = keyFactory.selectKey("RSA");
                        loadedKeyStore.loadKey(selectedTypeOfKey);
                    }

                    if (!loadedKeyStore.isKeyExistInKeyStore("HmacKey")) {

                        log.info(ENCRYPTION,"Hmac secretkey not exists in PKCS12 keystore");

                        var selectedTypeOfKey = keyFactory.selectKey("HmacSHA256");

                        selectedTypeOfKey.generateKey();

                        loadedKeyStore.saveKey(selectedTypeOfKey);

                        loadedKeyStore.saveToFile();


                    } else {

                        log.info(ENCRYPTION,"Hmac secretkey exists in PKCS12 keystore");
                        var selectedTypeOfKey = keyFactory.selectKey("HmacSHA256");
                        loadedKeyStore.loadKey(selectedTypeOfKey);
                    }

                }

            }
            loadedKeyStore.listAllKeys();
        }
        log.info(APP_INIT,"Success to Setup SecretKeys");
    }

}
