package hoomgroom.product.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.product.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public User extractUser(String token) {
        Claims claims = extractAllClaims(token);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(claims.get("user", Map.class), User.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isAdmin(UserDetails userDetails) {
        if (userDetails == null) {
            return false;
        }
        return userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }

    public boolean isAuthenticated(@NonNull HttpServletRequest request) {
        final String JWT_HEADER = "Authorization";
        final String JWT_TOKEN_PREFIX = "Bearer";
        final String authHeader = request.getHeader(JWT_HEADER);
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith(JWT_TOKEN_PREFIX)) {
            return false;
        }

        jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = extractUser(jwtToken);

        return isAdmin(userDetails);
    }
}