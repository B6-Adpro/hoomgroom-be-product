package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.dto.PercentagePromoRequest;
import hoomgroom.product.Promo.model.Factory.PercentagePromoFactory;
import hoomgroom.product.Promo.model.PercentagePromo;
import hoomgroom.product.Promo.repository.PercentagePromoRepository;
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
public class PercentagePromoServiceImplTest {
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
    void whenFindAllPercentagePromoShouldReturnListOfPercentagePromos() {
        List<PercentagePromo> allPromos = List.of(promo);

        when(promoRepository.findAll()).thenReturn(allPromos);

        List<PercentagePromo> result = promoService.findAll();
        verify(promoRepository, atLeastOnce()).findAll();
        assertEquals(allPromos, result);
    }

    @Test
    void whenFindByIdAndFoundShouldReturnPercentagePromo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));

        PercentagePromo result = promoService.findById(promo.getId());
        verify(promoRepository, atLeastOnce()).findById(any(java.util.UUID.class));
        assertEquals(promo.getId(), result.getId());
        assertEquals(promo.getName(), result.getName());
        assertEquals(promo.getDescription(), result.getDescription());
        assertEquals(promo.getPercentage(), result.getPercentage());
        assertEquals(promo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenFindByIdAndNotFoundShouldReturnEmpty() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> promoService.findById(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")));
    }

    @Test
    void whenCreatePercentagePromoShouldReturnTheCreatedPercentagePromo() {
        when(promoRepository.save(any(PercentagePromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, PercentagePromo.class);
            localPromo.setId(promo.getId());
            return localPromo;
        });


        PercentagePromo result = promoService.create(createRequest);
        verify(promoRepository, atLeastOnce()).save(any(PercentagePromo.class));
        assertEquals(promo.getId(), result.getId());
        assertEquals(promo.getName(), result.getName());
        assertEquals(promo.getDescription(), result.getDescription());
        assertEquals(promo.getPercentage(), result.getPercentage());
        assertEquals(promo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(promo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenUpdatePercentagePromoAndFoundShouldReturnTheUpdatedPercentagePromo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));
        when(promoRepository.save(any(PercentagePromo.class))).thenAnswer(invocation -> {
            var localPromo = invocation.getArgument(0, PercentagePromo.class);
            localPromo.setId(updatePromo.getId());
            return localPromo;
        });

        PercentagePromo result = promoService.update(updatePromo.getId(), updateRequest);
        verify(promoRepository, atLeastOnce()).save(any(PercentagePromo.class));
        assertEquals(updatePromo.getId(), result.getId());
        assertEquals(updatePromo.getName(), result.getName());
        assertEquals(updatePromo.getDescription(), result.getDescription());
        assertEquals(updatePromo.getPercentage(), result.getPercentage());
        assertEquals(updatePromo.getMinimumPurchase(), result.getMinimumPurchase());
        assertEquals(updatePromo.getExpirationDate(), result.getExpirationDate());
    }

    @Test
    void whenUpdatePercentagePromoAndNotFoundShouldThrowException() {
        when(promoRepository.findById(eq(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> promoService.update(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551"), createRequest));
    }

    @Test
    void whenDeletePercentagePromoAndFoundShouldCallDeleteByIdOnRepo() {
        when(promoRepository.findById(any(UUID.class))).thenReturn(Optional.of(promo));

        promoService.delete(promo.getId());
        verify(promoRepository, atLeastOnce()).delete(any(PercentagePromo.class));
    }

    @Test
    void whenDeletePercentagePromoAndNotFoundShouldThrowException() {
        when(promoRepository.findById(eq(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> promoService.delete(UUID.fromString("cf4a7487-83f5-4396-b115-608a8227b551")));
    }
}
