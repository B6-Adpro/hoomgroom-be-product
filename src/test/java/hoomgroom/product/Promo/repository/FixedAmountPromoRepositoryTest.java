package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.factory.FixedAmountPromoFactory;
import hoomgroom.product.promo.model.FixedAmountPromo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FixedAmountPromoRepositoryTest {
    @Autowired
    FixedAmountPromoRepository promoRepository;

    FixedAmountPromoFactory promoFactory = new FixedAmountPromoFactory();

    List<FixedAmountPromo> promos;

    @BeforeEach
    void setUp() {
        promos = new ArrayList<>();

        FixedAmountPromo promo1 = promoFactory.createPromo();
        promo1.setName("BELANJAHEMAT20000");
        promo1.setMinimumPurchase(20000000000L);
        promo1.setDescription("Diskon Belanja 20000 dengan minimal pembelian 20 milyar");
        promo1.setDiscountAmount(20000L);
        promos.add(promo1);

        FixedAmountPromo promo2 = promoFactory.createPromo();
        promo2.setName("BELANJAHEMAT10000");
        promo2.setMinimumPurchase(0L);
        promo2.setDiscountAmount(10000L);
        promos.add(promo2);

        FixedAmountPromo promo3 = promoFactory.createPromo();
        promo3.setName("BELANJAHEMAT5000");
        promo3.setMinimumPurchase(1000L);
        promo3.setDiscountAmount(5000L);
        promo3.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));
        promos.add(promo3);
    }

    @Test
    void testSaveCreate() {
        FixedAmountPromo promo = promos.get(2);
        promoRepository.save(promo);
        Optional<FixedAmountPromo> findResult = promoRepository.findById(promo.getId());

        assertTrue(findResult.isPresent());
        assertEquals(promo.getId(), findResult.get().getId());
        assertEquals(promo.getName(), findResult.get().getName());
        assertEquals(promo.getDescription(), findResult.get().getDescription());
        assertEquals(promo.getDiscountAmount(), findResult.get().getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), findResult.get().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), findResult.get().getExpirationDate());
    }

    @Test
    void testSaveUpdate() {
        FixedAmountPromo promo = promos.get(0);
        promoRepository.save(promo);

        FixedAmountPromo newPromo = promoFactory.createPromo();
        newPromo.setId(promo.getId());
        newPromo.setName(promo.getName());
        newPromo.setDescription(promo.getDescription());
        newPromo.setDiscountAmount(promo.getDiscountAmount());
        newPromo.setMinimumPurchase(promo.getMinimumPurchase());
        newPromo.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));

        Optional<FixedAmountPromo> findResult = promoRepository.findById(promo.getId());
        assertTrue(findResult.isPresent());
        assertEquals(promo.getId(), findResult.get().getId());
        assertEquals(promo.getName(), findResult.get().getName());
        assertEquals(promo.getDescription(), findResult.get().getDescription());
        assertEquals(promo.getDiscountAmount(), findResult.get().getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), findResult.get().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), findResult.get().getExpirationDate());
    }

    @Test
    void testFindByIdIfIdFound() {
        promoRepository.saveAll(promos);

        FixedAmountPromo target = promos.get(0);

        Optional<FixedAmountPromo> findResult = promoRepository.findById(target.getId());
        assertTrue(findResult.isPresent());
        assertEquals(target.getId(), findResult.get().getId());
        assertEquals(target.getName(), findResult.get().getName());
        assertEquals(target.getDescription(), findResult.get().getDescription());
        assertEquals(target.getDiscountAmount(), findResult.get().getDiscountAmount());
        assertEquals(target.getMinimumPurchase(), findResult.get().getMinimumPurchase());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        promoRepository.saveAll(promos);

        Optional<FixedAmountPromo> findResult = promoRepository.findById(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b"));
        assertTrue(findResult.isEmpty());
    }

    @Test
    void testFindByNameFound() {
        promoRepository.saveAll(promos);

        FixedAmountPromo target = promos.get(0);

        List<FixedAmountPromo> findResult = promoRepository.findByName(target.getName());
        assertTrue(findResult.contains(target));
        assertEquals(target.getId(), findResult.getFirst().getId());
        assertEquals(target.getName(), findResult.getFirst().getName());
        assertEquals(target.getDescription(), findResult.getFirst().getDescription());
        assertEquals(target.getDiscountAmount(), findResult.getFirst().getDiscountAmount());
        assertEquals(target.getMinimumPurchase(), findResult.getFirst().getMinimumPurchase());
        assertEquals(target.getExpirationDate(), findResult.getFirst().getExpirationDate());
    }

    @Test
    void testFindByNameNotFound() {
        promoRepository.saveAll(promos);

        List<FixedAmountPromo> findResult = promoRepository.findByName("name_not_found");
        assertTrue(findResult.isEmpty());
    }

    @Test
    void testFindByExpirationDateBeforeFound() {
        promoRepository.saveAll(promos);

        FixedAmountPromo target = promos.get(2);

        List<FixedAmountPromo> findResult = promoRepository.findByExpirationDateBefore(
                LocalDateTime.of(2024, 6, 5, 0, 0));
        assertTrue(findResult.contains(target));
        assertEquals(target.getId(), findResult.getFirst().getId());
        assertEquals(target.getName(), findResult.getFirst().getName());
        assertEquals(target.getDescription(), findResult.getFirst().getDescription());
        assertEquals(target.getDiscountAmount(), findResult.getFirst().getDiscountAmount());
        assertEquals(target.getMinimumPurchase(), findResult.getFirst().getMinimumPurchase());
        assertEquals(target.getExpirationDate(), findResult.getFirst().getExpirationDate());
    }

    @Test
    void testFindByExpirationDateBeforeNotFound() {
        promoRepository.saveAll(promos);

        List<FixedAmountPromo> findResult = promoRepository.findByExpirationDateBefore(
                LocalDateTime.of(2021, 6, 5, 0, 0));
        assertTrue(findResult.isEmpty());
    }

    @Test
    void testFindByDiscountAmountFound() {
        promoRepository.saveAll(promos);

        FixedAmountPromo target = promos.get(2);

        List<FixedAmountPromo> findResult = promoRepository.findByDiscountAmountBetween(0L, 9000L);
        assertEquals(1, findResult.size());
        assertEquals(target.getId(), findResult.getFirst().getId());
        assertEquals(target.getName(), findResult.getFirst().getName());
        assertEquals(target.getDescription(), findResult.getFirst().getDescription());
        assertEquals(target.getDiscountAmount(), findResult.getFirst().getDiscountAmount());
        assertEquals(target.getMinimumPurchase(), findResult.getFirst().getMinimumPurchase());
        assertEquals(target.getExpirationDate(), findResult.getFirst().getExpirationDate());
    }

    @Test
    void testFindByDiscountAmountNotFound() {
        promoRepository.saveAll(promos);

        List<FixedAmountPromo> findResult = promoRepository
                .findByDiscountAmountBetween(1000000000L, 3000000000L);

        assertTrue(findResult.isEmpty());
    }
}
