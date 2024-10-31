package boardProject.global.auth.encryption.strategy;

public interface TwoWayEncryptionStrategy extends EncryptionStrategy {

    @Override
    String encrypt(String plainText);

    @Override
    String decrypt(String encryptedText);
}
