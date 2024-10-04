package comatchingfc.comatchingfc.auth.jwt.refresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static comatchingfc.comatchingfc.auth.jwt.JwtExpirationConst.REFRESH_TOKEN_EXPIRATION;


@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public void saveRefreshToken(String uuid, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String uuid) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String uuid) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        redisTemplate.delete(key);
    }
}
