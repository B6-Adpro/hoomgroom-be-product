package hoomgroom.product.promo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentagePromoTest {
    PercentagePromo promo;


    @BeforeEach
    void setUp(){
        this.promo = new PercentagePromo();
        this.promo.setId(UUID.fromString("898e0bf5-9fa3-4815-92a5-0e9c422f9c3a"));
        this.promo.setName("BELANJAHEMAT20");
        this.promo.setDescription("Promo ini mengurangi harga sebesar 20 persen");
        this.promo.setMinimumPurchase(50000L);
        this.promo.setPercentage(20.0);
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
}
