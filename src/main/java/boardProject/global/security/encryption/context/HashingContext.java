package boardProject.global.security.encryption.context;

import boardProject.global.security.encryption.strategy.HashingStrategy;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.Setter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Setter
public class HashingContext {

    private HashingStrategy hashingStrategy;


    public HashingContext(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public String generateHash(String plainText)
            throws NoSuchAlgorithmException, InvalidKeyException {

        if (hashingStrategy == null) {
            throw new BusinessLogicException(StatusCode.HASHING_STRATEGY_NOT_SET);
        }

        return hashingStrategy.generateHash(plainText);
    }


    public boolean verifyHash(String plainText, String hashedText)
            throws NoSuchAlgorithmException, InvalidKeyException {

        if (hashingStrategy == null) {
            throw new BusinessLogicException(StatusCode.HASHING_STRATEGY_NOT_SET);
        }

        return hashingStrategy.verifyHash(plainText, hashedText);
    }


}
