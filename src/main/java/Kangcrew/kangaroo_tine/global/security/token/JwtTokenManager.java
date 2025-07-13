package Kangcrew.kangaroo_tine.global.security.token;

import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenManager implements TokenManager{

    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    public void initKey() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String writeToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("loginId", authentication.getName()); // kakaoId
        claims.put("id", authentication.getDetails());   // DB 내부 PK
        claims.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24시간
                .signWith(secretKey)
                .compact();
    }

    @Override
    public KangarootineAuthenticationToken readToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String loginId = claims.get("loginId", String.class);
        Long id = claims.get("id", Long.class);
        List<String> roles = claims.get("authorities", List.class);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(KangarootineAuthority.valueOf(role));
        }

        return new KangarootineAuthenticationToken(
                loginId, null, authorities, id
        );
    }
}