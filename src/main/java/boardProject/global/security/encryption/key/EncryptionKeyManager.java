package boardProject.global.security.encryption.key;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public interface EncryptionKeyManager {

    public void generateKey() throws CertificateException, OperatorCreationException, NoSuchAlgorithmException;

    public String getKeyAlgorithm();

    public int getKeySize();

    public String getKeyAlias();

    public char[] getKeyPassword();

    public boolean isKeyLoaded();




}
