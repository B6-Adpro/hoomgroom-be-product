package hoomgroom.product.promo.model;

import hoomgroom.product.promo.model.factory.FixedAmountPromoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedAmountPromoTest {
    FixedAmountPromo promo;

    FixedAmountPromoFactory factory = new FixedAmountPromoFactory();

    @BeforeEach
    void setUp(){
        this.promo = factory.createPromo();
        this.promo.setId(UUID.fromString("898e0bf5-9fa3-4815-92a5-0e9c422f9c3a"));
        this.promo.setName("BELANJAHEMAT20FIXED");
        this.promo.setDescription("Promo ini mengurangi harga sebesar 20 persen");
        this.promo.setMinimumPurchase(50000L);
        this.promo.setDiscountAmount(20000L);
        this.promo.setExpirationDate(LocalDateTime.now().plusHours(1));
    }

    @Test
    void testGetFixedAmountPromoId() {
        assertEquals(UUID.fromString("898e0bf5-9fa3-4815-92a5-0e9c422f9c3a"), this.promo.getId());
    }

    @Test
    void testGetFixedAmountPromoName() {
        assertEquals("BELANJAHEMAT20FIXED", this.promo.getName());
    }

    @Test
    void testGetFixedAmountPromoDescription() {
        assertEquals("Promo ini mengurangi harga sebesar 20 persen", this.promo.getDescription());
    }

    @Test
    void testGetFixedAmountPromoMinimumPurchase() {
        assertEquals(50000L, this.promo.getMinimumPurchase());
    }

    @Test
    void testGetFixedAmountPromoAmount() {
        assertEquals(20000L, this.promo.getDiscountAmount());
    }

    @Test
    void testGetPercentAmountExpirationDate() {
        assertEquals(LocalDateTime.now().plusHours(1).withNano(0),
                this.promo.getExpirationDate().withNano(0));}

    @Test
    void testApplyPromo() {
        long totalPrice = 60000L;
        long expectedDiscountedPrice = 40000L;
        long actualDiscountedPrice = promo.applyPromo(totalPrice);

        assertEquals(expectedDiscountedPrice, actualDiscountedPrice);
    }

    @Test
    void testApplyPromoInvalid() {
        long totalPrice = 30000L;
        long actualDiscountedPrice = promo.applyPromo(totalPrice);

        assertEquals(0, actualDiscountedPrice);
    }
}
