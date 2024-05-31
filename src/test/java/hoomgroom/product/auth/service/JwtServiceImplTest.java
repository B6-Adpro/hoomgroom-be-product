package hoomgroom.product.auth.service;

import hoomgroom.product.auth.model.Role;
import hoomgroom.product.auth.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;
    private String token;
    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl();
        String username = "testuser";
        String firstname = "test";
        String lastname = "register";
        UUID userId = UUID.fromString("72cad482-6781-4119-8296-780f43c000ee");
        Role userRole = Role.USER;
        user = User.builder()
                .id(userId)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .role(userRole)
                .build();
        token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5000 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void testExtractUsername() {
        String expectedUsername = "testuser";

        when(jwtService.extractUsername(token)).thenReturn(expectedUsername);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(expectedUsername, extractedUsername);
    }
    @Test
    void testExtractUser() {
        String username = "testuser";

        when(userDetails.getUsername()).thenReturn(username);
        User extractUser = jwtService.extractUser(token);

        assertNotNull(user);
    }

    @Test
    void testIsTokenValid_ValidTokenAndMatchingUser() {
        String username = "testuser";

        when(userDetails.getUsername()).thenReturn(username);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid_ValidTokenButNonMatchingUser() {
        String username = "anotheruser";

        when(userDetails.getUsername()).thenReturn(username);

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }
}
