package hoomgroom.product.promo.model;

import hoomgroom.product.promo.model.factory.PercentagePromoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentagePromoTest {
    PercentagePromo promo;

    PercentagePromoFactory factory = new PercentagePromoFactory();

    @BeforeEach
    void setUp(){
        this.promo = factory.createPromo();
        this.promo.setId(UUID.fromString("898e0bf5-9fa3-4815-92a5-0e9c422f9c3a"));
        this.promo.setName("BELANJAHEMAT20");
        this.promo.setDescription("Promo ini mengurangi harga sebesar 20 persen");
        this.promo.setMinimumPurchase(50000L);
        this.promo.setPercentage(20.0);
        this.promo.setExpirationDate(LocalDateTime.now().plusHours(1));
    }

    @Test
    void testGetPercentagePromoId() {
        assertEquals(UUID.fromString("898e0bf5-9fa3-4815-92a5-0e9c422f9c3a"), this.promo.getId());
    }

    @Test
    void testGetPercentagePromoName() {
        assertEquals("BELANJAHEMAT20", this.promo.getName());
    }

    @Test
    void testGetPercentagePromoDescription() {
        assertEquals("Promo ini mengurangi harga sebesar 20 persen", this.promo.getDescription());
    }

    @Test
    void testGetPercentagePromoMinimumPurchase() {
        assertEquals(50000L, this.promo.getMinimumPurchase());
    }

    @Test
    void testGetPercentagePromoPercentage() {
        assertEquals(20.0, this.promo.getPercentage());
    }

    @Test
    void testGetPercentAmountExpirationDate() {
        assertEquals(LocalDateTime.now().plusHours(1).withNano(0),
                this.promo.getExpirationDate().withNano(0));}

    @Test
    void testApplyPromo() {
        long totalPrice = 60000L;
        long expectedDiscountedPrice = 48000L;
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
