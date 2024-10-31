package boardProject.global.auth.encryption.keyStore;

import boardProject.global.auth.encryption.key.EncryptionKeyManager;
import boardProject.global.auth.encryption.key.asymmetric.RsaKey;
import boardProject.global.auth.encryption.key.factory.EncryptionKeyFactory;
import boardProject.global.auth.encryption.key.hmac.HmacKey;
import boardProject.global.auth.encryption.key.symmetric.AesKey;
import boardProject.global.auth.encryption.keyStore.properties.KeyStoreProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class JceksKeyStore implements KeyStoreManager {

    private final KeyStoreProperties keyStoreProperties;

    private final EncryptionKeyFactory keyFactory;

    private boolean isLoaded = false;

    private KeyStore keyStore;



    @Override
    public String getKeyStoreType() {
        return keyStoreProperties.getJecksKeyStoreType();
    }

    @Override
    public String getKeyStorePath() {
        return keyStoreProperties.getJecksKeyStorePath();
    }

    @Override
    public char[] getKeyStorePassword() {
        return keyStoreProperties.getJecksKeyStorePassword();
    }


    @Override
    public boolean isKeyStoreExist() {

        log.info("Attempt to check JCEKS keystore from file whether it exists");
        File keyStoreFile = new File(getKeyStorePath());
        log.info("{} keystore existence : {}",getKeyStoreType(), keyStoreFile.exists() && keyStoreFile.isFile());
        return keyStoreFile.exists() && keyStoreFile.isFile();
    }

    @Override
    public boolean isKeyExistInKeyStore (String alias) throws KeyStoreException {

        log.info("Attempt to check secretkey alias from keystore whether key exists");
        log.info("{} existence : {}", alias, keyStore.containsAlias(alias));
        return keyStore.containsAlias(alias);

    }


    @Override
    public boolean isLoaded() {
        return this.isLoaded;
    }




    @Override
    public void createKeyStore()
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {

        log.info("Attempt to create JCEKS KeyStore");
        this.keyStore = KeyStore.getInstance(getKeyStoreType());
        keyStore.load(null, getKeyStorePassword());

        isLoaded = true;

        log.info("Success to create JCEKS KeyStore");
    }

    @Override
    public void deleteKeyStore() {

        log.info("Attempt to delete JCEKS KeyStore");

        File keyStoreFile = new File(getKeyStorePath());

        if (!keyStoreFile.exists()) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_EXIST);
        }

        boolean deleted = keyStoreFile.delete();

        if (!deleted) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_DELETE_FAIL);
        }

        log.info("Success to delete JCEKS KeyStore file");
    }

    @Override
    public void saveToFile()
            throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {

        log.info("Attempt to save JCEKS KeyStore data to file");

       try (FileOutputStream fos = new FileOutputStream(getKeyStorePath())) {
           keyStore.store(fos, getKeyStorePassword());
       }

        log.info("Success to save JCEKS KeyStore data to file");

    }


    @Override
    public KeyStore loadFromFile()
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        log.info("Attempt to load JCEKS KeyStore data from file");

        if (isLoaded) {
            log.info("JCEKS KeyStore data is already loaded!");
            return keyStore;
        }


        this.keyStore = KeyStore.getInstance(getKeyStoreType());

        try (FileInputStream fileInputStream = new FileInputStream(getKeyStorePath())) {
            keyStore.load(fileInputStream, getKeyStorePassword());
        }


        isLoaded = true;

        log.info("Success to load JCEKS KeyStore data from file");

        return keyStore;
    }


    @Override
    public void unloadFromMemory()
            throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {

        log.info("Attempt to unload JCEKS KeyStore from JVM memory");

        if (!isLoaded) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }

        saveToFile();

        // 참조 끊어서 GC 대상으로 만들기
        keyStore = null;
        isLoaded = false;

        log.info("Success to unload JCEKS KeyStore from JVM memory");

    }


    @Override
    public void saveKey (EncryptionKeyManager keyManager) throws Exception {

        /** AES KEY -> JCEKS **/


        if(!isLoaded) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }

        switch (keyManager.getKeyAlgorithm()) {

            case "AES" -> {
                AesKey aesKey = (AesKey) keyManager;

                saveAesKey(aesKey);

            }
            case "RSA", "HmacSHA256" ->

                throw new BusinessLogicException(StatusCode.KEY_SAVED_WRONG_KEYSTORE);

        }

    }

    @Override
    public void saveAesKey(AesKey aesKeySingleton)
            throws KeyStoreException {


        log.info("Attempt to save AES secretkey to JCEKS KeyStore");

        SecretKey aesKey = aesKeySingleton.getAesKey();
        char[] keyPassword = aesKeySingleton.getKeyPassword();

        String alias = aesKeySingleton.getKeyAlias();
        var entry = new KeyStore.SecretKeyEntry(aesKey);
        var protParam = new KeyStore.PasswordProtection(keyPassword);

        keyStore.setEntry(alias, entry, protParam);

        log.info("Success to save AES secretkey to JCEKS KeyStore");
    }

    @Override
    public void saveRsaPrivateKey(RsaKey rsaKey) throws Exception {


        PrivateKey privateKey = rsaKey.getPrivateKey();
        char[] keyPassword = rsaKey.getKeyPassword();
        var chain = new Certificate[] {rsaKey.getCertificate()};

        String alias = rsaKey.getKeyAlias();
        var entry = new KeyStore.PrivateKeyEntry(privateKey,chain);
        var protParam = new KeyStore.PasswordProtection(keyPassword);

        keyStore.setEntry(alias, entry, protParam);

    }


    @Override
    public void saveHmacKey(HmacKey hmacKeySingleton) throws KeyStoreException {

        SecretKey hmacKey = hmacKeySingleton.getHmacKey();

        String alias = hmacKeySingleton.getKeyAlias();
        var entry = new KeyStore.SecretKeyEntry(hmacKey);

        keyStore.setEntry(alias, entry, null);
    }


    @Override
    public void loadKey(EncryptionKeyManager keyManager)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {

        if(!keyStore.containsAlias(keyManager.getKeyAlias())) {
            throw new BusinessLogicException(StatusCode.KEY_NOT_EXIST);
        }

        EncryptionKeyManager keySingleton = keyFactory.selectKey(keyManager.getKeyAlgorithm());

        if (keySingleton instanceof AesKey) {

            log.info("Attempt to load AES secretkey from JCEKS KeyStore");

            SecretKey aesKeyFromKeyStore =
                    (SecretKey) keyStore.getKey(keySingleton.getKeyAlias(),keySingleton.getKeyPassword());


            ((AesKey) keySingleton).loadKeyDataFromKeyStore(aesKeyFromKeyStore);

            log.info("Success to load AES secretkey to JCEKS KeyStore");
        }
    }


    public void listAllKeys() throws KeyStoreException {

        if (!isLoaded) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }

        String aliasesInfo = "";
        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            aliasesInfo += (alias + ", ");
        }

        log.info("Keys in PKCS12 keystore : {}", aliasesInfo);

    }






}
