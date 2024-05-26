package hoomgroom.product.promo.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedeemPromoRequestTest {

    @Test
    void testBuilder() {
        UUID transactionId = UUID.randomUUID();
        Long totalPrice = 100L;

        RedeemPromoRequest request = RedeemPromoRequest.builder()
                .transactionId(transactionId)
                .totalPrice(totalPrice)
                .build();

        assertEquals(transactionId, request.getTransactionId());
        assertEquals(totalPrice, request.getTotalPrice());
    }

    @Test
    void testToString() {
        assertEquals("RedeemPromoRequest.RedeemPromoRequestBuilder(transactionId=null, totalPrice=null)",
                RedeemPromoRequest.builder().toString());
    }
}
