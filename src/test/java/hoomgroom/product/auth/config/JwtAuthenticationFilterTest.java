package hoomgroom.product.auth.config;

import hoomgroom.product.auth.model.User;
import hoomgroom.product.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "validToken";
        User userDetails = mock(User.class);

        when(jwtService.extractUser(token)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        when(request.getHeader("Authorization")).thenReturn("Bearer" + token);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalRequestMissing() {
        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(null, response, filterChain)
        );
    }

    @Test
    void testDoFilterInternalResponseMissing() {
        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(request, null, filterChain)
        );
    }

    @Test
    void testDoFilterInternalFilterChainMissing() {
        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(request, response, null)
        );
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String token = "invalidToken";
        User userDetails = mock(User.class);

        when(jwtService.extractUser(token)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        when(request.getHeader("Authorization")).thenReturn("Bearer" + token);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(userDetails, never()).getAuthorities();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_AuthHeaderIsNull() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null); // authHeader is null

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUser(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_AuthHeaderWithoutPrefix() throws ServletException, IOException {
        String authHeader = "InvalidToken";

        when(request.getHeader("Authorization")).thenReturn(authHeader);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUser(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(filterChain).doFilter(request, response);
    }
}
