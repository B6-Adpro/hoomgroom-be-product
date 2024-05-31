package hoomgroom.product.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp(){
        this.product = new Product(
                UUID.fromString("8f5d3c7e-d0a2-4e35-9ec6-ee017aa104c6"),
                "Meja Kayu Semanggi",
                Arrays.asList("Meja", "Perabotan Kayu"),
                "Meja kayu dari PT Semanggi dengan desain terbaru.",
                "https://d248k8q1c80cf8.cloudfront.net/WK_Seito_0017_tif_584372fb43.jpg",
                50000L,
                20,
                5
        );
    }

    @Test
    void testGetProductId() {
        assertEquals(UUID.fromString("8f5d3c7e-d0a2-4e35-9ec6-ee017aa104c6"), this.product.getId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Meja Kayu Semanggi", this.product.getName());
    }

    @Test
    void testGetProductTags() {
        assertEquals(Arrays.asList("Meja", "Perabotan Kayu"), this.product.getTags());
    }

    @Test
    void testGetProductDescription() {
        assertEquals("Meja kayu dari PT Semanggi dengan desain terbaru.", this.product.getDescription());
    }

    @Test
    void testGetProductImageLink() {
        assertEquals("https://d248k8q1c80cf8.cloudfront.net/WK_Seito_0017_tif_584372fb43.jpg", this.product.getImageLink());
    }

    @Test
    void testGetProductOriginalPrice() {
        assertEquals(50000L, this.product.getOriginalPrice());
    }

    @Test
    void testGetProductDiscountPercentage() {
        assertEquals(20, this.product.getDiscountPercentage());
    }

    @Test
    void testGetProductTotalSales() {
        assertEquals(5, this.product.getTotalSales());
    }

    @Test
    void testGetProductDiscountedPrice() {
        assertEquals(40000L, this.product.getDiscountedPrice());
    }
}
