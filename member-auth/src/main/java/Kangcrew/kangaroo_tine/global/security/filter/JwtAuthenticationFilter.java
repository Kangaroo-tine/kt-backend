package Kangcrew.kangaroo_tine.global.security.filter;

import Kangcrew.kangaroo_tine.global.security.token.TokenManager;
import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.replace("Bearer ", "");

            try {
                KangarootineAuthenticationToken authToken = tokenManager.readToken(jwt);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException |
                     SignatureException e) {
                System.out.println("JWT 인증 실패: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
