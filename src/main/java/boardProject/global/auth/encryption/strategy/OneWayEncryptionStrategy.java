package boardProject.global.auth.encryption.strategy;

public interface OneWayEncryptionStrategy extends EncryptionStrategy {

    String algorithm = "";

    @Override
    String encrypt(String plainText);

    @Override
    String decrypt(String encryptedText);
}
