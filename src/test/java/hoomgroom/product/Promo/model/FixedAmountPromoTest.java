package hoomgroom.product.Promo.model;

import hoomgroom.product.Promo.model.Factory.FixedAmountPromoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedAmountPromoTest {
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
}