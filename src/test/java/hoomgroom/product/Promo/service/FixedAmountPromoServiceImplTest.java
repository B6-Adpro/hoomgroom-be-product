package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.factory.FixedAmountPromoFactory;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.repository.FixedAmountPromoRepository;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FixedAmountPromoServiceImplTest {
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
    void whenCreateFixedAmountPromoShouldReturnTheCreatedFixedAmountPromo() throws ExecutionException, InterruptedException {
        when(promoRepository.save(any(FixedAmountPromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, FixedAmountPromo.class);
            localPromo.setId(promo.getId());
            return localPromo;
        });


        CompletableFuture<PromoResponse> result = promoService.create(createRequest);
        verify(promoRepository, atLeastOnce()).save(any(FixedAmountPromo.class));
        assertEquals(promo.getId(), result.get().getPromo().getId());
        assertEquals(promo.getName(), result.get().getPromo().getName());
        assertEquals(promo.getDescription(), result.get().getPromo().getDescription());
        assertEquals(promo.getDiscountAmount(), ((FixedAmountPromo) result.get().getPromo()).getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), result.get().getPromo().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.get().getPromo().getExpirationDate());
    }

    @Test
    void whenUpdateFixedAmountPromoAndFoundShouldReturnTheUpdatedFixedAmountPromo() throws ExecutionException, InterruptedException {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));
        when(promoRepository.save(any(FixedAmountPromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, FixedAmountPromo.class);
            localPromo.setId(updatePromo.getId());
            return localPromo;
        });

        CompletableFuture<PromoResponse> result = promoService.update(updatePromo.getId(), updateRequest);
        verify(promoRepository, atLeastOnce()).save(any(FixedAmountPromo.class));
        assertEquals(promo.getId(), result.get().getPromo().getId());
        assertEquals(promo.getName(), result.get().getPromo().getName());
        assertEquals(promo.getDescription(), result.get().getPromo().getDescription());
        assertEquals(promo.getDiscountAmount(), ((FixedAmountPromo) result.get().getPromo()).getDiscountAmount());
        assertEquals(promo.getMinimumPurchase(), result.get().getPromo().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.get().getPromo().getExpirationDate());
    }
}
