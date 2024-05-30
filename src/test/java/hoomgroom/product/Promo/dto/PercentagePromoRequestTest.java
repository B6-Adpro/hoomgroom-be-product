package hoomgroom.product.promo.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentagePromoRequestTest {

    @Test
    void testBuilder() {
        String name = "Test Promo";
        String description = "Test Description";
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(7);
        Long minimumPurchase = 100L;
        Double percentage = 10.0;

        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name(name)
                .description(description)
                .expirationDate(expirationDate)
                .minimumPurchase(minimumPurchase)
                .percentage(percentage)
                .build();

        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
        assertEquals(expirationDate, request.getExpirationDate());
        assertEquals(minimumPurchase, request.getMinimumPurchase());
        assertEquals(percentage, request.getPercentage());
    }

    @Test
    void testToString() {
        assertEquals("PercentagePromoRequest.PercentagePromoRequestBuilder(name=null, description=null, expirationDate=null, minimumPurchase=null, percentage=null)",
                PercentagePromoRequest.builder().toString());
    }
}
