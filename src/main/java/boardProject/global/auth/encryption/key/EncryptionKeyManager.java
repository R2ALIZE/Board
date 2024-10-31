package boardProject.global.auth.encryption.key;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Component;

import java.security.cert.CertificateException;

@Component
public interface EncryptionKeyManager {

    public void generateKey() throws CertificateException, OperatorCreationException;

    public String getKeyAlgorithm();

    public int getKeySize();

    public String getKeyAlias();

    public char[] getKeyPassword();

    public boolean isKeyLoaded();




}
