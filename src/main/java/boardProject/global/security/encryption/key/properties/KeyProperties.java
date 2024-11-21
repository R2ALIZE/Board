package boardProject.global.security.encryption.key.properties;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties (prefix = "key")
public class KeyProperties {


    private AesKeyProperties aes = new AesKeyProperties();

    private RsaKeyProperties rsa = new RsaKeyProperties();

    private HmacKeyProperties hmac = new HmacKeyProperties();


    /** aesKey getter **/

    public String getAesAlgorithm () {
        return aes.getAlgorithm();
    }

    public int getAesKeySize () {
        return aes.getSize();
    }

    public String getAesKeyAlias () {
        return aes.getAlias();
    }

    public char[] getAesKeyPassword () {
        return aes.getPassword();
    }


    /** rsaPrivateKey getter **/

    public String getRsaAlgorithm () {
        return rsa.getAlgorithm();
    }

    public int getRsaKeySize () {
        return rsa.getSize();
    }

    public String getRsaKeyAlias () {
        return rsa.getAlias();
    }

    public char[] getRsaKeyPassword () {
        return rsa.getPassword();
    }

    /** hamcKey getter **/

    public String getHmacKeyAlgorithm () {
        return hmac.getAlgorithm();
    }

    public String getHmacKeyAlias () {
        return hmac.getAlias();
    }

    public char[] getHmacKeyPassword () {return hmac.getPassword();}



    @Getter
    @Setter
    public static class AesKeyProperties {

        private String algorithm;

        private int size;

        private String alias;

        private String password;

        public char[] getPassword() {
            return password != null ? password.toCharArray() : new char[0];
        }


    }

    @Getter
    @Setter
    public static class RsaKeyProperties {

        private String algorithm;

        private int size;

        private String alias;

        private String password;

        public char[] getPassword() {
            return password != null ? password.toCharArray() : new char[0];
        }

    }

    @Getter
    @Setter
    public static class HmacKeyProperties {

        private String algorithm;

        private String alias;

        private String password;

        public char[] getPassword() {
            return password != null ? password.toCharArray() : new char[0];
        }

    }



}
