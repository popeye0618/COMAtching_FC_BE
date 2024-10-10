package comatchingfc.comatchingfc.auth.jwt;

import comatchingfc.comatchingfc.exception.BusinessException;
import comatchingfc.comatchingfc.user.enums.UserRole;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import static comatchingfc.comatchingfc.auth.jwt.JwtExpirationConst.*;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private SecretKey aesKey;

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ENCRYPTION_MODE = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret, @Value("${aes.secret}") String aesSecret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] jwtHash = digest.digest(jwtSecret.getBytes(StandardCharsets.UTF_8));
            byte[] aesHash = digest.digest(aesSecret.getBytes(StandardCharsets.UTF_8));

            secretKey = new SecretKeySpec(jwtHash, "HmacSHA256");
            aesKey = new SecretKeySpec(aesHash, ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

    public String getUUID(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("uuid", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] iv = cipher.getIV();
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            byte[] encryptedWithIv = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encryptedData, 0, encryptedWithIv, iv.length, encryptedData.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);

        } catch (Exception e) {
            throw new BusinessException(ResponseCode.JWT_ERROR);
        }
    }

    // 복호화 메소드
    public String decrypt(String encryptedData) {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
            byte[] iv = new byte[12]; // GCM 모드의 IV 길이는 12바이트
            System.arraycopy(decodedData, 0, iv, 0, iv.length);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);

            byte[] originalData = cipher.doFinal(decodedData, iv.length, decodedData.length - iv.length);
            return new String(originalData, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new BusinessException(ResponseCode.JWT_ERROR);
        }
    }

    public String generateAccessToken(String uuid, String role) {
        if (role.equals(UserRole.ROLE_USER.toString()) || role.equals(UserRole.ROLE_PENDING.toString())) {
            return generateToken(uuid, role, USER_ACCESS_TOKEN_EXPIRATION);
        }
        return generateToken(uuid, role, ADMIN_ACCESS_TOKEN_EXPIRATION);
    }
    public String generateRefreshToken(String uuid, String role) {
        return generateToken(uuid, role, REFRESH_TOKEN_EXPIRATION);
    }

    public String generateToken(String uuid, String role, long expiredMs) {
        String tokenData = Jwts.builder()
                .claim("uuid", uuid)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();

        return encrypt(tokenData);
    }

    public String decryptToken(String encryptedToken) {
        return decrypt(encryptedToken);
    }
}
