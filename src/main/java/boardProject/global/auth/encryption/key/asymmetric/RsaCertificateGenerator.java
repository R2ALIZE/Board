package boardProject.global.auth.encryption.key.asymmetric;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Component
public class RsaCertificateGenerator {

    // BouncyCastle Provider를 초기화합니다.
    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * RSA 인증서를 생성합니다.
     *
     * @param keyPair  RSA 키 쌍
     * @param cn       인증서의 Common Name (CN)
     * @param validityDays 인증서 유효 기간(일 단위)
     * @return 생성된 X.509 인증서
     * @throws CertificateException 인증서 생성 실패 시 예외
     * @throws OperatorCreationException 서명 생성 실패 시 예외
     */
    public X509Certificate generateRsaCertificate(KeyPair keyPair, String cn, int validityDays)
            throws CertificateException, OperatorCreationException {

        var certBuilder = createCertificateBuilder(keyPair, cn, validityDays);

        // 서명 생성기 준비
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        // 인증서 생성
        X509CertificateHolder certHolder = certBuilder.build(signer);
        return new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(certHolder);
    }

    @NotNull
    private JcaX509v3CertificateBuilder createCertificateBuilder(KeyPair keyPair, String cn, int validityDays) {
        X500Principal subject = new X500Principal("CN=" + cn);

        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + validityDays * 24L * 60 * 60 * 1000); // 유효 기간 설정

        BigInteger serialNumber = new BigInteger(64, new SecureRandom());
        return new JcaX509v3CertificateBuilder(
                subject, serialNumber, notBefore, notAfter, subject, keyPair.getPublic()
        );
    }
}
