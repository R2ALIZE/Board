package boardProject.global.auth.encryption.keyStore;

import boardProject.global.auth.encryption.key.EncryptionKeyManager;
import boardProject.global.auth.encryption.key.asymmetric.RsaKey;
import boardProject.global.auth.encryption.key.hmac.HmacKey;
import boardProject.global.auth.encryption.key.symmetric.AesKey;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public interface KeyStoreManager {

    public String getKeyStoreType();

    public String getKeyStorePath();

    public char[] getKeyStorePassword();

    public boolean isKeyStoreExist();

    public boolean isKeyExistInKeyStore (String alias) throws KeyStoreException;

    public boolean isLoaded();

    public void createKeyStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException;

    public void deleteKeyStore();

    public void saveToFile() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException;

    public KeyStore loadFromFile() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;

    public void unloadFromMemory() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException;

    public void saveKey (EncryptionKeyManager keyManager) throws Exception;

    public void saveAesKey(AesKey aesKey) throws KeyStoreException;

    public void saveRsaPrivateKey(RsaKey rsaKey) throws Exception;

    public void saveHmacKey(HmacKey hmacKey) throws KeyStoreException;

    void loadKey(EncryptionKeyManager keyManager)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, OperatorCreationException;

    public void listAllKeys() throws KeyStoreException;




}
