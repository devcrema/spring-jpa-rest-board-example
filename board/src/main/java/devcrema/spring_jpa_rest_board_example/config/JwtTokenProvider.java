package devcrema.spring_jpa_rest_board_example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final String SECRET_KEY = "THIS_IS_JWT_SECRET_KEY!!";
    private static final String ENCODED_SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_HEADER_PREFIX = "Bearer ";

    private static final long EXPIRE_TIME = 1000L * 60L * 60L * 24L * 365L;

    @Qualifier("userService")
    private final UserDetailsService userDetailsService;

    public String createToken(String userId, Collection<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, ENCODED_SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(ENCODED_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenHeader = "";
        try{
            tokenHeader = request.getHeader(TOKEN_HEADER)
                    .split(TOKEN_HEADER_PREFIX, 2)[1];
        } catch (NullPointerException exception){
            log.info("header is not valid : " + request.getHeader(TOKEN_HEADER));
        }
        return tokenHeader;
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(ENCODED_SECRET_KEY)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }
}
