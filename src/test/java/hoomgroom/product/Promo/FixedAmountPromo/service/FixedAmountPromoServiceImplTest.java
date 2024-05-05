package hoomgroom.product.Promo.FixedAmountPromo.service;

import hoomgroom.product.Promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.Promo.model.Factory.FixedAmountPromoFactory;
import hoomgroom.product.Promo.model.FixedAmountPromo;
import hoomgroom.product.Promo.repository.FixedAmountPromoRepository;
import hoomgroom.product.Promo.service.FixedAmountPromoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FixedAmountPromoServiceImplTest {
    @InjectMocks
    FixedAmountPromoServiceImpl promoService;

    @Mock
    FixedAmountPromoRepository promoRepository;

    FixedAmountPromoFactory promoFactory = new FixedAmountPromoFactory();

    FixedAmountPromo promo;
    FixedAmountPromo updatePromo;
    FixedAmountPromoRequest createRequest;
    FixedAmountPromoRequest updateRequest;

    @BeforeEach
    void setUp() {
        promo = promoFactory.createPromo();
        promo.setId(UUID.fromString("dcb2dff0-0cb0-4a79-98ab-8f0ec1bf39f7"));
        promo.setName("BELANJAHEMAT20000");
        promo.setDescription("Diskon belanja 20000");
        promo.setDiscountAmount(20000L);
        promo.setMinimumPurchase(100000L);
        promo.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));

        updatePromo = promoFactory.createPromo();
        updatePromo.setId(UUID.fromString("2a727aac-8788-4242-b609-5752386f929c"));
        updatePromo.setName("BELANJAHEMAT10000");
        updatePromo.setDescription("Diskon belanja 10000");
        updatePromo.setDiscountAmount(10000L);
        updatePromo.setMinimumPurchase(50000L);
        updatePromo.setExpirationDate(LocalDateTime.of(2024, 6, 4, 0, 0));

        createRequest = FixedAmountPromoRequest.builder()
                .name("BELANJAHEMAT20000")
                .description("Diskon belanja 20000")
                .discountAmount(20000L)
                .minimumPurchase(100000L)
                .expirationDate(LocalDateTime.of(2024, 6, 5, 0, 0))
                .build();

        updateRequest = FixedAmountPromoRequest.builder()
                .name("BELANJAHEMAT10000")
                .description("Diskon belanja 10000")
                .discountAmount(10000L)
                .minimumPurchase(50000L)
                .expirationDate(LocalDateTime.of(2024, 6, 4, 0, 0))
                .build();
    }

    @Test
    void whenFindAllFixedAmountPromoShouldReturnListOfFixedAmountPromos() {
        List<FixedAmountPromo> allPromos = List.of(promo);

        when(promoRepository.findAll()).thenReturn(allPromos);

        List<FixedAmountPromo> result = promoService.findAll();
        verify(promoRepository, atLeastOnce()).findAll();
        assertEquals(allPromos, result);
    }

    @Test
    void whenFindByIdAndFoundShouldReturnFixedAmountPromo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));

        FixedAmountPromo result = promoService.findById(promo.getId());
        verify(promoRepository, atLeastOnce()).findById(any(java.util.UUID.class));
        assertEquals(promo.getId(), result.getId());
        assertEquals(promo.getName(), result.getName());
        assertEquals(promo.getDescription(), result.getDescription());
        assertEquals(promo.getDiscountAmount(), result.getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenFindByIdAndNotFoundShouldReturnEmpty() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            promoService.findById(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551"));
        });
    }

    @Test
    void whenCreateFixedAmountPromoShouldReturnTheCreatedFixedAmountPromo() {
        when(promoRepository.save(any(FixedAmountPromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, FixedAmountPromo.class);
            localPromo.setId(promo.getId());
            return localPromo;
        });


        FixedAmountPromo result = promoService.create(createRequest);
        verify(promoRepository, atLeastOnce()).save(any(FixedAmountPromo.class));
        assertEquals(promo.getId(), result.getId());
        assertEquals(promo.getName(), result.getName());
        assertEquals(promo.getDescription(), result.getDescription());
        assertEquals(promo.getDiscountAmount(), result.getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenUpdateFixedAmountPromoAndFoundShouldReturnTheUpdatedFixedAmountPromo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));
        when(promoRepository.save(any(FixedAmountPromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, FixedAmountPromo.class);
            localPromo.setId(updatePromo.getId());
            return localPromo;
        });

        FixedAmountPromo result = promoService.update(updatePromo.getId(), updateRequest);
        verify(promoRepository, atLeastOnce()).save(any(FixedAmountPromo.class));
        assertEquals(updatePromo.getId(), result.getId());
        assertEquals(updatePromo.getName(), result.getName());
        assertEquals(updatePromo.getDescription(), result.getDescription());
        assertEquals(updatePromo.getDiscountAmount(), result.getDiscountAmount());
        assertEquals(updatePromo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(updatePromo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenUpdateFixedAmountPromoAndNotFoundShouldThrowException() {
        when(promoRepository.findById(eq(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            promoService.update(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551"), createRequest);
        });
    }

    @Test
    void whenDeleteFixedAmountPromoAndFoundShouldCallDeleteByIdOnRepo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));

        promoService.delete(promo.getId());
        verify(promoRepository, atLeastOnce()).delete(any(FixedAmountPromo.class));
    }

    @Test
    void whenDeleteFixedAmountPromoAndNotFoundShouldThrowException() {
        when(promoRepository.findById(eq(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            promoService.delete(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551"));
        });
    }
}
