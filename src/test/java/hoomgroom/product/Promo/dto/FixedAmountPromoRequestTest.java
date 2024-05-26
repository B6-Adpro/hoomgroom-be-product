package hoomgroom.product.promo.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedAmountPromoRequestTest {

    @Test
    void testBuilder() {
        String name = "Test Promo";
        String description = "Test Description";
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(7);
        Long minimumPurchase = 100L;
        Long discountAmount = 50L;

        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name(name)
                .description(description)
                .expirationDate(expirationDate)
                .minimumPurchase(minimumPurchase)
                .discountAmount(discountAmount)
                .build();

        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
        assertEquals(expirationDate, request.getExpirationDate());
        assertEquals(minimumPurchase, request.getMinimumPurchase());
        assertEquals(discountAmount, request.getDiscountAmount());
    }

    @Test
    void testToString() {
        assertEquals("FixedAmountPromoRequest(name=null, description=null, expirationDate=null, minimumPurchase=null, discountAmount=null)",
                FixedAmountPromoRequest.builder().build().toString());
    }
}
