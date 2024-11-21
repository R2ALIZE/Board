package boardProject.global.security.encryption.keyStore;

import boardProject.global.security.encryption.key.EncryptionKeyManager;
import boardProject.global.security.encryption.key.asymmetric.RsaKey;
import boardProject.global.security.encryption.key.factory.EncryptionKeyFactory;
import boardProject.global.security.encryption.key.hmac.HmacKey;
import boardProject.global.security.encryption.key.symmetric.AesKey;
import boardProject.global.security.encryption.keyStore.properties.KeyStoreProperties;
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

import static boardProject.global.logging.LogMarkerFactory.ENCRYPTION;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class Pkcs12KeyStore implements KeyStoreManager {

    private boolean isLoaded = false;

    private final KeyStoreProperties keyStoreProperties;

    private final EncryptionKeyFactory keyFactory;

    private KeyStore keyStore;


    public String getKeyStoreType() {
        return keyStoreProperties.getPkcs12KeyStoreType();
    }

    public String getKeyStorePath() {
        return keyStoreProperties.getPkcs12KeyStorePath();
    }

    public char[] getKeyStorePassword() {
        return keyStoreProperties.getPkcs12KeyStorePassword();
    }


    @Override
    public boolean isKeyStoreExist() {

        File keyStoreFile = new File(getKeyStorePath());
        return keyStoreFile.exists() && keyStoreFile.isFile();
    }


    public boolean isKeyExistInKeyStore (String alias) throws KeyStoreException {
        return keyStore.containsAlias(alias);
    }


    @Override
    public boolean isLoaded() {
        return this.isLoaded;
    }


    @Override
    public void createKeyStore()
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {

        log.info(ENCRYPTION,"Attempt to create PKCS12 KeyStore");

        this.keyStore = KeyStore.getInstance(getKeyStoreType());
        keyStore.load(null, getKeyStorePassword());
        isLoaded = true;

        log.info(ENCRYPTION,"Success to create PKCS12 KeyStore");
    }

    @Override
    public void deleteKeyStore() {

        log.info(ENCRYPTION,"Attempt to delete PKCS12 KeyStore");

        File keyStoreFile = new File(getKeyStorePath());

        if (!keyStoreFile.exists()) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_EXIST);
        }

        boolean deleted = keyStoreFile.delete();

        if (!deleted) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_DELETE_FAIL);
        }

        log.info(ENCRYPTION,"Success to delete PKCS12 KeyStore file");
    }

    @Override
    public void saveToFile()
            throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {

        log.info(ENCRYPTION,"Attempt to save PKCS12 KeyStore info to file");

        try (FileOutputStream fos = new FileOutputStream(getKeyStorePath())) {
            keyStore.store(fos, getKeyStorePassword());

       }

        log.info(ENCRYPTION,"Success to save PKCS12 KeyStore date to file");

    }


    @Override
    public KeyStore loadFromFile()
            throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {

        log.info(ENCRYPTION,"Attempt to load PKCS12 KeyStore from file");


        if (isLoaded) {
            log.info(ENCRYPTION,"PKCS12 Keystore is already loaded");
            return keyStore;
        }

        this.keyStore = KeyStore.getInstance(getKeyStoreType());

        try (FileInputStream fileInputStream = new FileInputStream(getKeyStorePath())) {
            keyStore.load(fileInputStream, getKeyStorePassword());
            isLoaded = true;
        }

        log.info(ENCRYPTION,"Success to load PKCS12 KeyStore from file");

        return keyStore;
    }


    @Override
    public void unloadFromMemory()
            throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {

        log.info(ENCRYPTION,"Attempt to unload PKCS12 KeyStore from JVM memory");

        if (isLoaded) {

            saveToFile();

            // 참조 끊어서 GC 대상으로 만들기
            keyStore = null;
            isLoaded = false;

            log.info(ENCRYPTION,"Success to unload PKCS12 KeyStore from JVM memory");
        } else {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }
    }


    @Override
    public void saveKey (EncryptionKeyManager keyManager) throws Exception {

        /**
            AES KEY -> JCEKS
            RSA HAMC -> PKCS12
         **/

        if(!isLoaded) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }

        if (keyManager instanceof RsaKey rsaKey) {
            saveRsaPrivateKey(rsaKey);
        }

        if (keyManager instanceof HmacKey hmacKey) {
            saveHmacKey(hmacKey);
        }

        if (keyManager instanceof AesKey) {
            throw new BusinessLogicException(StatusCode.KEY_SAVED_WRONG_KEYSTORE);
        }


    }

    @Override
    public void saveAesKey(AesKey aesKeySingleton)
            throws KeyStoreException {


        SecretKey aesKey = aesKeySingleton.getAesKey();
        char[] keyPassword = aesKeySingleton.getKeyPassword();

        String alias = aesKeySingleton.getKeyAlias();
        var entry = new KeyStore.SecretKeyEntry(aesKey);
        var protParam = new KeyStore.PasswordProtection(keyPassword);

        keyStore.setEntry(alias, entry, protParam);
    }

    @Override
    public void saveRsaPrivateKey(RsaKey rsaKey) throws Exception {

        log.info(ENCRYPTION,"Attempt to save RSA private key to PKCS12 KeyStore");


        PrivateKey privateKey = rsaKey.getPrivateKey();
        char[] keyPassword = rsaKey.getKeyPassword();
        var chain = new Certificate[] {rsaKey.getCertificate()};

        String alias = rsaKey.getKeyAlias();
        var entry = new KeyStore.PrivateKeyEntry(privateKey,chain);
        var protParam = new KeyStore.PasswordProtection(keyPassword);

        keyStore.setEntry(alias, entry, protParam);

        log.info(ENCRYPTION,"Success to save RSA private key to PKCS12 KeyStore");

    }


    @Override
    public void saveHmacKey(HmacKey hmacKeySingleton) throws KeyStoreException {

        log.info(ENCRYPTION,"Attempt to save Hmac secretkey to PKCS12 KeyStore");

        SecretKey hmacKey = hmacKeySingleton.getHmacKey();
        char[] keyPassword = hmacKeySingleton.getKeyPassword();

        String alias = hmacKeySingleton.getKeyAlias();
        var entry = new KeyStore.SecretKeyEntry(hmacKey);
        var protParam = new KeyStore.PasswordProtection(keyPassword);

        keyStore.setEntry(alias, entry, protParam);

        log.info(ENCRYPTION,"Success to save Hmac secretkey PKCS12 KeyStore");
    }

    @Override
    public void loadKey(EncryptionKeyManager keyManager)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {

        /**
            PKCS12 -> RSA, HMAC
            JECKS -> AES
        **/


        if(!keyStore.containsAlias(keyManager.getKeyAlias())) {
            throw new BusinessLogicException(StatusCode.KEY_NOT_EXIST);
        }

        EncryptionKeyManager keySingleton = keyFactory.selectKey(keyManager.getKeyAlgorithm());


        if (keySingleton instanceof RsaKey) {

            log.info(ENCRYPTION,"Attempt to load RSA private key from PKCS12 KeyStore");

            PrivateKey privateKeyFromKeyStore =
                    (PrivateKey) keyStore.getKey(keySingleton.getKeyAlias(),keySingleton.getKeyPassword());

            log.info(ENCRYPTION,"Attempt to load RSA Certificate from PKCS12 KeyStore");
            Certificate certificateFromKeyStore = keyStore.getCertificate(keySingleton.getKeyAlias());
            ((RsaKey) keySingleton).loadCertFromKeyStore(certificateFromKeyStore);
            log.info(ENCRYPTION,"Success to load RSA Certificate from PKCS12 KeyStore");


            log.info(ENCRYPTION,"Attempt to extract RSA public key from Certificate");
            PublicKey publicKeyFromKeyStore = certificateFromKeyStore.getPublicKey();
            log.info(ENCRYPTION,"Success to extract RSA public key from Certificate");

            log.info("Attempt to combine private key and public key to create keypair");
            KeyPair keyPairFromKeyStore = new KeyPair(publicKeyFromKeyStore,privateKeyFromKeyStore);

            log.info(ENCRYPTION,"Attempt to change RSA keyPair to loaded Key data");
            ((RsaKey) keySingleton).loadKeyDataFromKeyStore(keyPairFromKeyStore);
            log.info(ENCRYPTION,"Success to change RSA keyPair to loaded Key data");


            log.info(ENCRYPTION,"RSA Keypair Loaded ? : {}",keySingleton.isKeyLoaded());
            log.info(ENCRYPTION,"Success to load RSA private key from PKCS12 KeyStore");
        }

        if (keySingleton instanceof HmacKey) {

            log.info(ENCRYPTION,"Attempt to load Hmac secretkey from PKCS12 KeyStore");

            SecretKey hmackeyFromKeyStore =
                    (SecretKey) keyStore.getKey(keySingleton.getKeyAlias(),keySingleton.getKeyPassword());

            ((HmacKey) keySingleton).loadKeyDataFromKeyStore(hmackeyFromKeyStore);


            log.info(ENCRYPTION,"Hmac secretkey Loaded ? : {}",keySingleton.isKeyLoaded());
            log.info(ENCRYPTION,"Success to load Hmac secretkey from PKCS12 KeyStore");
        }
    }

    @Override
    public void listAllKeys() throws KeyStoreException {

        if (!isLoaded) {
            throw new BusinessLogicException(StatusCode.KEYSTORE_NOT_LOADED);
        }

        StringBuilder aliasesInfo = new StringBuilder();
        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            aliasesInfo.append(alias).append(", ");
        }

        log.info(ENCRYPTION,"Keys in PKCS12 keystore : {}", aliasesInfo.toString());
    }





}
