package boardProject.global.auth.encryption.keyStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeyStoreFactory {

    private final Map<String, KeyStoreManager> keyStoreMap = new HashMap<>();


    @Autowired
    public KeyStoreFactory (JceksKeyStore jceksKeyStore, Pkcs12KeyStore pkcs12KeyStore) {

        keyStoreMap.put("JCEKS",jceksKeyStore);
        keyStoreMap.put("PKCS12",pkcs12KeyStore);
    }


    public KeyStoreManager selectKeyStore(String keyStoreType) {

        KeyStoreManager keyStoreManager = keyStoreMap.get(keyStoreType);

        return keyStoreManager;

    }
}