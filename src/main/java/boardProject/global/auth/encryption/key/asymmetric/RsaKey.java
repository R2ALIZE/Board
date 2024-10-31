package boardProject.global.auth.encryption.key.asymmetric;

import boardProject.global.auth.encryption.key.EncryptionKeyManager;
import boardProject.global.auth.encryption.key.properties.KeyProperties;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
@RequiredArgsConstructor
public class RsaKey implements EncryptionKeyManager {

    @Getter
    private KeyPair rsaKey;

    @Getter
    private X509Certificate certificate;

    private final RsaCertificateGenerator certificateGenerator;

    private final KeyProperties keyProperties;





    public void generateKey() throws CertificateException, OperatorCreationException {
        try {

            String keyAlgorithm = keyProperties.getRsaAlgorithm();
            int keySize = keyProperties.getRsaKeySize();

            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGenerator.initialize(keySize, new SecureRandom());

            this.rsaKey = keyGenerator.generateKeyPair();

            this.certificate
                    = certificateGenerator.generateRsaCertificate(
                            rsaKey,
                    "localhost",
                    365
            );

        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException(StatusCode.KEY_GENERATE_FAIL);
        }
    }




    @Override
    public String getKeyAlgorithm() {
        return keyProperties.getRsaAlgorithm();
    }

    @Override
    public int getKeySize() {
        return keyProperties.getRsaKeySize();
    }

    @Override
    public String getKeyAlias() {
        return keyProperties.getRsaKeyAlias();
    }

    @Override
    public char[] getKeyPassword() {
        return keyProperties.getRsaKeyPassword();
    }

    @Override
    public boolean isKeyLoaded() {
        return rsaKey != null;
    }

    public PublicKey getPublicKey() {
        return rsaKey.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return rsaKey.getPrivate();
    }


    public void loadKeyDataFromKeyStore(KeyPair keyDataFromKeyStore) {
        this.rsaKey = keyDataFromKeyStore;
    }
    public void loadCertFromKeyStore(Certificate certificateFromKeyStore) {
        this.certificate = (X509Certificate) certificateFromKeyStore;
    }
}