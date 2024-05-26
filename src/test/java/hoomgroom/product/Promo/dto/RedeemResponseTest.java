package hoomgroom.product.promo.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedeemResponseTest {

    @Test
    void testBuilder() {
        String message = "Test message";
        UUID transactionId = UUID.randomUUID();
        UUID promoId = UUID.randomUUID();
        Long normalPrice = 100L;
        Long discountPrice = 90L;

        RedeemResponse response = RedeemResponse.builder()
                .message(message)
                .transactionId(transactionId)
                .promoId(promoId)
                .normalPrice(normalPrice)
                .discountPrice(discountPrice)
                .build();

        assertEquals(message, response.getMessage());
        assertEquals(transactionId, response.getTransactionId());
        assertEquals(promoId, response.getPromoId());
        assertEquals(normalPrice, response.getNormalPrice());
        assertEquals(discountPrice, response.getDiscountPrice());
    }

    @Test
    void testToString() {
        assertEquals("RedeemResponse.RedeemResponseBuilder(message=null, transactionId=null, promoId=null, normalPrice=null, discountPrice=null)",
                RedeemResponse.builder().toString());
    }
}
