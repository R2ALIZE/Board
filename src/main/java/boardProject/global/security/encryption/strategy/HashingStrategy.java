package boardProject.global.security.encryption.strategy;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface HashingStrategy {

    public String generateHash(String plainText) throws NoSuchAlgorithmException, InvalidKeyException;

    public boolean verifyHash (String plainText, String hashed) throws NoSuchAlgorithmException, InvalidKeyException;

}
