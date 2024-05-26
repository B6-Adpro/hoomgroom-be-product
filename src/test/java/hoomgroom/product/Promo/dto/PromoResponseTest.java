package hoomgroom.product.promo.dto;

import hoomgroom.product.promo.model.Promo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PromoResponseTest {

    @Test
    void testBuilder() {
        String message = "Test message";
        Promo promo = mock(Promo.class);

        PromoResponse response = PromoResponse.builder()
                .message(message)
                .promo(promo)
                .build();

        assertEquals(message, response.getMessage());
        assertEquals(promo, response.getPromo());
    }

    @Test
    void testToString() {
        assertEquals("PromoResponse.PromoResponseBuilder(message=null, promo=null)",
                PromoResponse.builder().toString());
    }
}
