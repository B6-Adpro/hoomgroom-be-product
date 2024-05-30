package hoomgroom.product.auth.service;

import hoomgroom.product.auth.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsInVzZXIiOnsiaWQiOiI3MmNhZDQ4Mi02NzgxLTQxMTktODI5Ni03ODBmNDNjMDAwZWUiLCJ1c2VybmFtZSI6InRlc3R1c2VyIiwiZmlyc3RuYW1lIjoidGVzdCIsImxhc3RuYW1lIjoicmVnaXN0ZXIiLCJyb2xlIjoiVVNFUiJ9LCJpYXQiOjE3MTcwOTA0MjUsImV4cCI6MTcxNzEwMTIyNX0.zF-pv1rIOcX-E5xgV-9COSPmc4le2wPkdHvbMNPMjAY";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl();
    }

    @Test
    void testExtractUsername() {
        String expectedUsername = "testuser";

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(expectedUsername, extractedUsername);
    }
    @Test
    void testExtractUser() {

        User user = jwtService.extractUser(token);

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("test", user.getFirstname());
        assertEquals("register", user.getLastname());
        assertEquals(UUID.fromString("72cad482-6781-4119-8296-780f43c000ee"), user.getId());
        assertEquals("USER", user.getRole().name());
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
