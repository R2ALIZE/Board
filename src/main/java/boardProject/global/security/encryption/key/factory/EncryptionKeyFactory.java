package boardProject.global.security.encryption.key.factory;

import boardProject.global.security.encryption.key.EncryptionKeyManager;
import boardProject.global.security.encryption.key.asymmetric.RsaKey;
import boardProject.global.security.encryption.key.hmac.HmacKey;
import boardProject.global.security.encryption.key.symmetric.AesKey;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EncryptionKeyFactory {

    private final Map<String, EncryptionKeyManager> keyMap = new HashMap<>();

    @Autowired
    public EncryptionKeyFactory(AesKey aesKey, RsaKey rsaKey, HmacKey hmacKey) {
        keyMap.put("AES", aesKey);
        keyMap.put("RSA", rsaKey);
        keyMap.put("HmacSHA256", hmacKey);
    }

    public EncryptionKeyManager selectKey(String keyType) {
        EncryptionKeyManager keyManager = keyMap.get(keyType);
        if (keyManager == null) {
            throw new BusinessLogicException(StatusCode.KEY_TYPE_NOT_FOUND);
        }
        return keyManager;
    }
}
