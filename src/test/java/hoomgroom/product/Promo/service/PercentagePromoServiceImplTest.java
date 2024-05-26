package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.factory.PercentagePromoFactory;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.repository.PercentagePromoRepository;
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
class PercentagePromoServiceImplTest {
    @InjectMocks
    PercentagePromoServiceImpl promoService;

    @Mock
    PercentagePromoRepository promoRepository;

    PercentagePromoFactory promoFactory = new PercentagePromoFactory();

    PercentagePromo promo;
    PercentagePromo updatePromo;
    PercentagePromoRequest createRequest;
    PercentagePromoRequest updateRequest;

    @BeforeEach
    void setUp() {
        promo = promoFactory.createPromo();
        promo.setId(UUID.fromString("dcb2dff0-0cb0-4a79-98ab-8f0ec1bf39f7"));
        promo.setName("BELANJAHEMAT20");
        promo.setDescription("Diskon belanja 20%");
        promo.setPercentage(20.0);
        promo.setMinimumPurchase(100000L);
        promo.setExpirationDate(LocalDateTime.of(2024, 6, 5, 0, 0));

        updatePromo = promoFactory.createPromo();
        updatePromo.setId(UUID.fromString("2a727aac-8788-4242-b609-5752386f929c"));
        updatePromo.setName("BELANJAHEMAT10");
        updatePromo.setDescription("Diskon belanja 10%");
        updatePromo.setPercentage(10.0);
        updatePromo.setMinimumPurchase(50000L);
        updatePromo.setExpirationDate(LocalDateTime.of(2024, 6, 4, 0, 0));

        createRequest = PercentagePromoRequest.builder()
                .name("BELANJAHEMAT20")
                .description("Diskon belanja 20%")
                .percentage(20.000)
                .minimumPurchase(100000L)
                .expirationDate(LocalDateTime.of(2024, 6, 5, 0, 0))
                .build();

        updateRequest = PercentagePromoRequest.builder()
                .name("BELANJAHEMAT10")
                .description("Diskon belanja 10%")
                .percentage(10.0)
                .minimumPurchase(50000L)
                .expirationDate(LocalDateTime.of(2024, 6, 4, 0, 0))
                .build();
    }

    @Test
    void whenCreatePercentagePromoShouldReturnTheCreatedPercentagePromo() throws ExecutionException, InterruptedException {
        when(promoRepository.save(any(PercentagePromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, PercentagePromo.class);
            localPromo.setId(promo.getId());
            return localPromo;
        });


        CompletableFuture<PromoResponse> result = promoService.create(createRequest);
        verify(promoRepository, atLeastOnce()).save(any(PercentagePromo.class));
        assertEquals(promo.getId(), result.get().getPromo().getId());
        assertEquals(promo.getName(), result.get().getPromo().getName());
        assertEquals(promo.getDescription(), result.get().getPromo().getDescription());
        assertEquals(promo.getPercentage(), ((PercentagePromo) result.get().getPromo()).getPercentage());
        assertEquals(promo.getMinimumPurchase(), result.get().getPromo().getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.get().getPromo().getExpirationDate());
    }

    @Test
    void whenUpdatePercentagePromoAndFoundShouldReturnTheUpdatedPercentagePromo() throws ExecutionException, InterruptedException {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));
        when(promoRepository.save(any(PercentagePromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, PercentagePromo.class);
            localPromo.setId(updatePromo.getId());
            return localPromo;
        });

        CompletableFuture<PromoResponse> result = promoService.update(updatePromo.getId(), updateRequest);
        verify(promoRepository, atLeastOnce()).save(any(PercentagePromo.class));
        assertEquals(updatePromo.getId(), result.get().getPromo().getId());
        assertEquals(updatePromo.getName(), result.get().getPromo().getName());
        assertEquals(updatePromo.getDescription(), result.get().getPromo().getDescription());
        assertEquals(updatePromo.getPercentage(), ((PercentagePromo) result.get().getPromo()).getPercentage());
        assertEquals(updatePromo.getMinimumPurchase(), result.get().getPromo().getMinimumPurchase());
        assertEquals(updatePromo.getExpirationDate(), result.get().getPromo().getExpirationDate());
    }
}
