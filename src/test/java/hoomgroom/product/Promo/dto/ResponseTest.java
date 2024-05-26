package hoomgroom.product.promo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    @Test
    void testBuilder() {
        String message = "Test message";

        Response response = Response.builder()
                .message(message)
                .build();

        assertEquals(message, response.getMessage());
    }

    @Test
    void testToString() {
        assertEquals("Response.ResponseBuilder(message=null)", Response.builder().toString());
    }
}
