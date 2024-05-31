package hoomgroom.product.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    Collection<? extends GrantedAuthority> authorities;
    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        user = User.builder()
                .id(userId)
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .role(Role.USER)
                .build();
        authorities = user.getAuthorities();
    }
    @Test
    void testBuilder() {
        assertEquals("username", user.getUsername());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testToString() {
        assertEquals(
            "User.UserBuilder(id=null, username=null, firstname=null, lastname=null, role=null)",
            User.builder().toString()
        );
    }

    @Test
    void testGetUserPassword() {
        assertNull(user.getPassword());
    }
    @Test
    void testGetUserAccountIsNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testGetUserAccountIsNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testGetUserCredentialIsNonLocked() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testGetUserIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testGetUserAuthorities() {
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
