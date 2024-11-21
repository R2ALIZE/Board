package boardProject.global.security.encryption.key.asymmetric;

import boardProject.global.security.encryption.key.EncryptionKeyManager;
import boardProject.global.security.encryption.key.properties.KeyProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static boardProject.global.logging.LogMarkerFactory.ENCRYPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class RsaKey implements EncryptionKeyManager {

    @Getter
    private KeyPair rsaKey;

    @Getter
    private X509Certificate certificate;

    private final RsaCertificateGenerator certificateGenerator;

    private final KeyProperties keyProperties;





    public void generateKey()
            throws CertificateException, OperatorCreationException, NoSuchAlgorithmException {

            log.info(ENCRYPTION,"Attempt to generate RSA keyPair");

            String keyAlgorithm = keyProperties.getRsaAlgorithm();
            int keySize = keyProperties.getRsaKeySize();



            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGenerator.initialize(keySize, new SecureRandom());

            this.rsaKey = keyGenerator.generateKeyPair();

            log.info(ENCRYPTION,"Attempt to generate certificate for RSA");

            this.certificate
                = certificateGenerator.generateRsaCertificate(
                rsaKey,
                "localhost",
                365
            );

            log.info(ENCRYPTION,"Success to generate certificate");


            log.info("Success to generate RSA keyPair");


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