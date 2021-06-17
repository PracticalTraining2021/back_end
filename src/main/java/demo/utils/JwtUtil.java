package demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil implements InitializingBean {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expires_second}")
    private long expiresSecond;

    private Key key;

    public static final String TOKEN_HEADER_KEY = "Authorization";
    public static final String TOKEN_START_WITH = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";

    public String createToken(String userId) {
        return Jwts.builder()
                .claim(AUTHORITIES_KEY, userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiresSecond + System.currentTimeMillis()))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(key)
                .compact();
    }

    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claim(AUTHORITIES_KEY, claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiresSecond + System.currentTimeMillis()))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            //如果过期,在异常中调用, 返回claims, 否则无法解析过期的token
            claims = e.getClaims();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}