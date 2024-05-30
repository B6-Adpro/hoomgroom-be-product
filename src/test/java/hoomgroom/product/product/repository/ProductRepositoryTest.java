package hoomgroom.product.product.repository;

import hoomgroom.product.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();

        Product product1 = new Product(
                UUID.fromString("8f5d3c7e-d0a2-4e35-9ec6-ee017aa104c6"),
                "Meja Kayu Semanggi",
                Arrays.asList("Meja", "Perabotan Kayu"),
                "Meja kayu dari PT Semanggi dengan desain terbaru.",
                "https://d248k8q1c80cf8.cloudfront.net/WK_Seito_0017_tif_584372fb43.jpg",
                50000L,
                20,
                5
        );
        this.products.add(product1);

        Product product2 = new Product(
                UUID.fromString("e2938df2-3bd6-46df-9df3-e5dac79ed493"),
                "Meja Minimalis Taichan",
                Arrays.asList("Meja", "Perabotan Modern", "Murah"),
                "Meja berkesan modern dengan desain minimalis.",
                "https://i.pinimg.com/originals/d5/ed/2e/d5ed2e335399325f82d1204d4128ff74.jpg",
                25000L,
                0,
                3
        );
        this.products.add(product2);

        Product product3 = new Product(
                UUID.fromString("41fca77b-e6fa-44f2-b24a-d7bccb3ac50f"),
                "Kursi Kayu Tekpro",
                Arrays.asList("Kursi", "Perabotan Kayu"),
                "Kursi kayu elegan, diukir oleh profesional.",
                "https://www.eshopregal.in/wp-content/uploads/2022/06/Cris-wooden-Jali-chair-1.jpg",
                150000L,
                10,
                0
        );
        this.products.add(product3);
    }

    @Test
    void testSaveCreate() {
        Product product = this.products.getFirst();
        productRepository.save(product);
        Optional<Product> savedProduct = productRepository.findById(product.getId());

        assertTrue(savedProduct.isPresent());
        assertEquals(product.getId(), savedProduct.get().getId());
        assertEquals(product.getName(), savedProduct.get().getName());
        assertEquals(product.getTags(), savedProduct.get().getTags());
        assertEquals(product.getDescription(), savedProduct.get().getDescription());
        assertEquals(product.getImageLink(), savedProduct.get().getImageLink());
        assertEquals(product.getOriginalPrice(), savedProduct.get().getOriginalPrice());
        assertEquals(product.getDiscountPercentage(), savedProduct.get().getDiscountPercentage());
        assertEquals(product.getTotalSales(), savedProduct.get().getTotalSales());
    }

    @Test
    void testSaveUpdate() {
        Product product = this.products.getFirst();
        productRepository.save(product);

        Product updatedProduct = new Product(
                product.getId(),
                product.getName(),
                product.getTags(),
                product.getDescription(),
                product.getImageLink(),
                product.getOriginalPrice(),
                product.getDiscountPercentage(),
                6
        );
        productRepository.save(product);

        Optional<Product> savedProduct = productRepository.findById(product.getId());

        assertTrue(savedProduct.isPresent());
        assertEquals(product.getId(), savedProduct.get().getId());
        assertEquals(product.getName(), savedProduct.get().getName());
        assertEquals(product.getTags(), savedProduct.get().getTags());
        assertEquals(product.getDescription(), savedProduct.get().getDescription());
        assertEquals(product.getImageLink(), savedProduct.get().getImageLink());
        assertEquals(product.getOriginalPrice(), savedProduct.get().getOriginalPrice());
        assertEquals(product.getDiscountPercentage(), savedProduct.get().getDiscountPercentage());
        assertEquals(6, savedProduct.get().getTotalSales());
    }

    @Test
    void testFindByIdIfFound() {
        productRepository.saveAll(products);

        Product product = products.getFirst();
        Optional<Product> savedProduct = productRepository.findById(product.getId());

        assertTrue(savedProduct.isPresent());
        assertEquals(product.getId(), savedProduct.get().getId());
        assertEquals(product.getName(), savedProduct.get().getName());
        assertEquals(product.getTags(), savedProduct.get().getTags());
        assertEquals(product.getDescription(), savedProduct.get().getDescription());
        assertEquals(product.getImageLink(), savedProduct.get().getImageLink());
        assertEquals(product.getOriginalPrice(), savedProduct.get().getOriginalPrice());
        assertEquals(product.getDiscountPercentage(), savedProduct.get().getDiscountPercentage());
        assertEquals(product.getTotalSales(), savedProduct.get().getTotalSales());
    }

    @Test
    void testFindByIdIfNotFound() {
        productRepository.saveAll(products);

        Optional<Product> savedProduct = productRepository.findById(UUID.fromString("5948f2c2-84aa-4ece-bd5e-24ffa7f70fa6"));

        assertTrue(savedProduct.isEmpty());
    }
}
