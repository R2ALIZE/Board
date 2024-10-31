package boardProject.global.auth.encryption.keyStore.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "keystore")
public class KeyStoreProperties {

    public JceksProperties jceks = new JceksProperties();

    public Pkcs12Properties pkcs12 = new Pkcs12Properties();

    /** JecksKeyStore getters**/


    public String getJecksKeyStoreType () {
        return jceks.getType();
    }
    public String getJecksKeyStorePath () {
        return jceks.getPath();
    }
    public char[] getJecksKeyStorePassword () {
        return jceks.getPassword();
    }


    /** pkcs12KeyStore getters**/

    public String getPkcs12KeyStoreType () {
        return pkcs12.getType();
    }
    public String getPkcs12KeyStorePath () {
        return pkcs12.getPath();
    }
    public char[] getPkcs12KeyStorePassword () {
        return pkcs12.getPassword();
    }



    @Getter
    @Setter
    public static class JceksProperties {
        private String type;
        private String path;
        private String password;

        public char[] getPassword() {
            return password != null ? password.toCharArray() : new char[0];
        }
    }


    @Getter
    @Setter
    public static class Pkcs12Properties {
        private String type;
        private String path;
        private String password;


        public char[] getPassword() {
            return password != null ? password.toCharArray() : new char[0];
        }


    }



}
