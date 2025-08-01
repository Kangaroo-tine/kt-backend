package Kangcrew.kangaroo_tine.global.security.token;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import Kangcrew.kangaroo_tine.global.exception.GeneralException;
import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineAuthority;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
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

    private final UserRepository userRepository;

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
    public String writeRefreshToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +7 * 24 * 60 * 60 * 1000L))
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

        Long id = claims.get("id", Long.class);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        KangarootineUserDetails userDetails = new KangarootineUserDetails(user);

        return new KangarootineAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities(),
                user.getId()
        );
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String kakaoId = claims.getSubject();

        return new KangarootineAuthenticationToken(kakaoId, null);
    }
}