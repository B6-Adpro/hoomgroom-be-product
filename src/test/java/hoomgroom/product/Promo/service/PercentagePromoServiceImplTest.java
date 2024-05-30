package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.repository.PercentagePromoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PercentagePromoServiceImplTest {

    @Mock
    private PercentagePromoRepository percentagePromoRepository;

    @InjectMocks
    private PercentagePromoServiceImpl promoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PercentagePromo promo1 = new PercentagePromo();
        PercentagePromo promo2 = new PercentagePromo();
        List<PercentagePromo> promoList = Arrays.asList(promo1, promo2);

        when(percentagePromoRepository.findAll()).thenReturn(promoList);

        ResponseEntity<List<PercentagePromo>> responseEntity = promoService.findAll();

        assertEquals(promoList, responseEntity.getBody());
    }

    @Test
    void testIsNotExpired() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));

        when(percentagePromoRepository.findById(UUID.randomUUID())).thenReturn(java.util.Optional.of(promo));

        boolean result = promoService.isNotExpired(promo);

        assertTrue(result);
    }

    @Test
    void testIsExpired() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));

        when(percentagePromoRepository.findById(UUID.randomUUID())).thenReturn(java.util.Optional.of(promo));

        boolean result = promoService.isNotExpired(promo);

        assertFalse(result);
    }

    @Test
    void testIsNotNegativeDiscount() {
        PercentagePromo promo = new PercentagePromo();
        promo.setPercentage(10.0);

        boolean result = promoService.isNotNegativeDiscount(promo);

        assertTrue(result);
    }

    @Test
    void testIsNegativeDiscount() {
        PercentagePromo promo = new PercentagePromo();
        promo.setPercentage(-10.0);

        boolean result = promoService.isNotNegativeDiscount(promo);

        assertFalse(result);
    }

    @Test
    void testIsNotNegativeMinPurchase() {
        PercentagePromo promo = new PercentagePromo();
        promo.setMinimumPurchase(100L);

        boolean result = promoService.isNotNegativeMinPurchase(promo);

        assertTrue(result);
    }

    @Test
    void testIsNegativeMinPurchase() {
        PercentagePromo promo = new PercentagePromo();
        promo.setMinimumPurchase(-50L);

        boolean result = promoService.isNotNegativeMinPurchase(promo);

        assertFalse(result);
    }

    @Test
    void testIsValidPromo() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setPercentage(10.0);

        boolean result = promoService.isValid(promo);

        assertTrue(result);
    }

    @Test
    void testIsInvalidPromo_Expired() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setPercentage(10.0);

        boolean result = promoService.isValid(promo);

        assertFalse(result);
    }

    @Test
    void testIsInvalidPromo_NegativeMinPurchase() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(-100L);
        promo.setPercentage(10.0);

        boolean result = promoService.isValid(promo);

        assertFalse(result);
    }

    @Test
    void testIsInvalidPromo_NegativeDiscount() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setPercentage(-10.0);

        boolean result = promoService.isValid(promo);

        assertFalse(result);
    }

    @Test
    void testIsInvalidPromo_ExpiredAndNegativeMinPurchase() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(-100L);
        promo.setPercentage(10.0);

        boolean result = promoService.isValid(promo);

        assertFalse(result);
    }

    @Test
    void testIsInvalidPromo_ExpiredAndNegativeDiscount() {
        PercentagePromo promo = new PercentagePromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setPercentage(-10.0);

        boolean result = promoService.isValid(promo);

        assertFalse(result);
    }

    @Test
    void testCreateValidPromo() {
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(100L)
                .percentage(10.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        ResponseEntity<PromoResponse> responseEntity = promoService.create(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCreateInvalidPromo_NegativeDiscount() {
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Invalid Promo")
                .description("negative discount promo")
                .minimumPurchase(100L)
                .percentage(-10.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        ResponseEntity<PromoResponse> responseEntity = promoService.create(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_PromoFoundButNotValid() {
        UUID id = UUID.randomUUID();
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(-50L)
                .percentage(20.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        PercentagePromo existingPromo = new PercentagePromo();
        existingPromo.setId(id);
        existingPromo.setName("Existing Promo");
        existingPromo.setDescription("This is an existing promo");
        existingPromo.setMinimumPurchase(50L);
        existingPromo.setPercentage(10.0);
        existingPromo.setExpirationDate(LocalDateTime.now().plusDays(2));

        when(percentagePromoRepository.findById(id)).thenReturn(Optional.of(existingPromo));

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Percent Promo update failed! Invalid field(s)!", responseEntity.getBody().getMessage());
    }

    @Test
    void testUpdate_Success() {
        UUID id = UUID.randomUUID();
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(100L)
                .percentage(20.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        PercentagePromo existingPromo = new PercentagePromo();
        existingPromo.setId(id);
        existingPromo.setName("Existing Promo");
        existingPromo.setDescription("This is an existing promo");
        existingPromo.setMinimumPurchase(50L);
        existingPromo.setPercentage(10.0);
        existingPromo.setExpirationDate(LocalDateTime.now().plusDays(2));

        PercentagePromo updatedPromo = new PercentagePromo();
        updatedPromo.setId(id);
        updatedPromo.setName(request.getName());
        updatedPromo.setDescription(request.getDescription());
        updatedPromo.setMinimumPurchase(request.getMinimumPurchase());
        updatedPromo.setPercentage(request.getPercentage());
        updatedPromo.setExpirationDate(request.getExpirationDate());

        when(percentagePromoRepository.findById(id)).thenReturn(Optional.of(existingPromo));
        when(percentagePromoRepository.save(any())).thenReturn(updatedPromo);

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Percent Promo updated successfully!", responseEntity.getBody().getMessage());
    }

    @Test
    void testUpdate_PromoNotFound() {
        UUID id = UUID.randomUUID();
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(100L)
                .percentage(20.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        when(percentagePromoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Percent Promo not found", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getPromo());
    }

    @Test
    void testUpdate_PromoFoundButNotValidMinimum() {
        UUID id = UUID.randomUUID();
        PercentagePromoRequest request = PercentagePromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(-50L)
                .percentage(20.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        PercentagePromo existingPromo = new PercentagePromo();
        existingPromo.setId(id);
        existingPromo.setName("Existing Promo");
        existingPromo.setDescription("This is an existing promo");
        existingPromo.setMinimumPurchase(50L);
        existingPromo.setPercentage(10.0);
        existingPromo.setExpirationDate(LocalDateTime.now().plusDays(2));

        when(percentagePromoRepository.findById(id)).thenReturn(Optional.of(existingPromo));

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Percent Promo update failed! Invalid field(s)!", responseEntity.getBody().getMessage());
    }
}
