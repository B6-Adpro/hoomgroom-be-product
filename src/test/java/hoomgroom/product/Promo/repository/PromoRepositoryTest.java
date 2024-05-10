package hoomgroom.product.Promo.repository;

import hoomgroom.product.Promo.model.Factory.PercentagePromoFactory;
import hoomgroom.product.Promo.model.PercentagePromo;
import hoomgroom.product.Promo.model.Promo;
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
public class PromoRepositoryTest {
    @Autowired
    PromoRepository promoRepository;

    PercentagePromoFactory promoFactory = new PercentagePromoFactory();

    List<PercentagePromo> promos;

    @BeforeEach
    void setUp() {
        promos = new ArrayList<>();

        PercentagePromo promo1 = promoFactory.createPromo();
        promo1.setName("BELANJAHEMAT20");
        promo1.setMinimumPurchase(20000000000L);
        promo1.setDescription("Diskon Belanja 20000 dengan minimal pembelian 20 milyar");
        promo1.setPercentage(20.0);
        promos.add(promo1);

        PercentagePromo promo2 = promoFactory.createPromo();
        promo2.setName("BELANJAHEMAT10");
        promo2.setMinimumPurchase(0L);
        promo2.setPercentage(10.0);
        promos.add(promo2);

        PercentagePromo promo3 = promoFactory.createPromo();
        promo3.setName("BELANJAHEMAT5");
        promo3.setMinimumPurchase(1000L);
        promo3.setPercentage(5.0);
        promo3.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));
        promos.add(promo3);
    }

    @Test
    public void testSaveCreate() {
        PercentagePromo promo = promos.get(2);
        promoRepository.save(promo);
        Optional<Promo> findResult = promoRepository.findById(promo.getId());

        assertTrue(findResult.isPresent());
        assertEquals(promo.getId(), findResult.get().getId());
        assertEquals(promo.getName(), findResult.get().getName());
        assertEquals(promo.getDescription(), findResult.get().getDescription());
        assertEquals(promo.getMinimumPurchase(), findResult.get().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), findResult.get().getExpirationDate());
    }

    @Test
    public void testSaveUpdate() {
        PercentagePromo promo = promos.get(0);
        promoRepository.save(promo);

        PercentagePromo newPromo = promoFactory.createPromo();
        newPromo.setId(promo.getId());
        newPromo.setName(promo.getName());
        newPromo.setDescription(promo.getDescription());
        newPromo.setPercentage(promo.getPercentage());
        newPromo.setMinimumPurchase(promo.getMinimumPurchase());
        newPromo.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));

        Optional<Promo> findResult = promoRepository.findById(promo.getId());
        assertTrue(findResult.isPresent());
        assertEquals(promo.getId(), findResult.get().getId());
        assertEquals(promo.getName(), findResult.get().getName());
        assertEquals(promo.getDescription(), findResult.get().getDescription());
        assertEquals(promo.getMinimumPurchase(), findResult.get().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), findResult.get().getExpirationDate());
    }

    @Test
    public void testFindByIdIfIdFound() {
        promoRepository.saveAll(promos);

        PercentagePromo target = promos.get(0);

        Optional<Promo> findResult = promoRepository.findById(target.getId());
        assertTrue(findResult.isPresent());
        assertEquals(target.getId(), findResult.get().getId());
        assertEquals(target.getName(), findResult.get().getName());
        assertEquals(target.getDescription(), findResult.get().getDescription());
        assertEquals(target.getMinimumPurchase(), findResult.get().getMinimumPurchase());
    }

    @Test
    public void testFindByIdIfIdNotFound() {
        promoRepository.saveAll(promos);

        Optional<Promo> findResult = promoRepository.findById(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b"));
        assertTrue(findResult.isEmpty());
    }
}