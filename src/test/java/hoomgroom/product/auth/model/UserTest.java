package hoomgroom.product.auth.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testBuilder() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .role(Role.USER)
                .build();

        assertEquals(userId, user.getId());
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
}
