package boardProject.global.auth.encryption.strategy;

public interface AsymmetricKeyStrategy extends TwoWayEncryptionStrategy{

    @Override
    String encrypt(String plainText);

    @Override
    String decrypt(String encryptedText);
}
