package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.repository.FixedAmountPromoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class FixedAmountPromoServiceImplTest {

    @Mock
    private FixedAmountPromoRepository fixedAmountPromoRepository;

    @InjectMocks
    private FixedAmountPromoServiceImpl promoService;

    @Test
    void testFindAll() {
        FixedAmountPromo promo1 = new FixedAmountPromo();
        promo1.setId(UUID.randomUUID());
        promo1.setName("Promo 1");
        FixedAmountPromo promo2 = new FixedAmountPromo();
        promo2.setId(UUID.randomUUID());
        promo2.setName("Promo 2");
        List<FixedAmountPromo> promoList = Arrays.asList(promo1, promo2);

        when(fixedAmountPromoRepository.findAll()).thenReturn(promoList);

        ResponseEntity<List<FixedAmountPromo>> responseEntity = promoService.findAll();

        assertEquals(promo1, responseEntity.getBody().get(0));
        assertEquals(promo2, responseEntity.getBody().get(1));
    }

    @Test
    void testIsNotExpired() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        assertTrue(promoService.isNotExpired(promo));
    }

    @Test
    void testIsExpired() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        assertFalse(promoService.isNotExpired(promo));
    }

    @Test
    void testIsNotNegativeMinPurchase() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setMinimumPurchase(100L);
        assertTrue(promoService.isNotNegativeMinPurchase(promo));
    }

    @Test
    void testIsNegativeMinPurchase() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setMinimumPurchase(-50L);
        assertFalse(promoService.isNotNegativeMinPurchase(promo));
    }

    @Test
    void testIsNotNegativeDiscount() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setDiscountAmount(50L);
        assertTrue(promoService.isNotNegativeDiscount(promo));
    }

    @Test
    void testIsNegativeDiscount() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setDiscountAmount(-25L);
        assertFalse(promoService.isNotNegativeDiscount(promo));
    }

    @Test
    void testIsValid_ValidPromo() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setDiscountAmount(10L);
        assertTrue(promoService.isValid(promo));
    }

    @Test
    void testIsValid_ExpiredOnly() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setDiscountAmount(10L);
        assertFalse(promoService.isValid(promo));
    }

    @Test
    void testIsValid_NegativeMinPurchaseOnly() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(-50L);
        promo.setDiscountAmount(10L);
        assertFalse(promoService.isValid(promo));
    }

    @Test
    void testIsValid_NegativeDiscountOnly() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().plusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setDiscountAmount(-25L);
        assertFalse(promoService.isValid(promo));
    }

    @Test
    void testIsValid_ExpiredAndNegativeMinPurchase() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(-50L);
        promo.setDiscountAmount(10L);
        assertFalse(promoService.isValid(promo));
    }

    @Test
    void testIsValid_ExpiredAndNegativeDiscount() {
        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setExpirationDate(LocalDateTime.now().minusDays(1));
        promo.setMinimumPurchase(100L);
        promo.setDiscountAmount(-25L);
        assertFalse(promoService.isValid(promo));
    }

    @Test
    void testCreate_ValidPromo() {
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name("Valid Promo")
                .description("This is a valid promo")
                .minimumPurchase(100L)
                .discountAmount(10L)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        FixedAmountPromo promo = new FixedAmountPromo();
        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setMinimumPurchase(request.getMinimumPurchase());
        promo.setDiscountAmount(request.getDiscountAmount());
        promo.setExpirationDate(request.getExpirationDate());

        when(fixedAmountPromoRepository.save(any())).thenReturn(promo);

        ResponseEntity<PromoResponse> responseEntity = promoService.create(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Create Fixed Promo Success", responseEntity.getBody().getMessage());
    }

    @Test
    void testCreate_InvalidPromo() {
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name("Invalid Promo")
                .description("This promo is invalid")
                .minimumPurchase(-50L)
                .discountAmount(10L)
                .expirationDate(LocalDateTime.now().minusDays(1))
                .build();

        ResponseEntity<PromoResponse> responseEntity = promoService.create(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_Success() {
        UUID id = UUID.randomUUID();
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(100L)
                .discountAmount(10L)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        FixedAmountPromo existingPromo = new FixedAmountPromo();
        existingPromo.setId(id);
        existingPromo.setName("Existing Promo");
        existingPromo.setDescription("This is an existing promo");
        existingPromo.setMinimumPurchase(50L);
        existingPromo.setDiscountAmount(5L);
        existingPromo.setExpirationDate(LocalDateTime.now().plusDays(2));

        FixedAmountPromo updatedPromo = new FixedAmountPromo();
        updatedPromo.setId(id);
        updatedPromo.setName(request.getName());
        updatedPromo.setDescription(request.getDescription());
        updatedPromo.setMinimumPurchase(request.getMinimumPurchase());
        updatedPromo.setDiscountAmount(request.getDiscountAmount());
        updatedPromo.setExpirationDate(request.getExpirationDate());

        when(fixedAmountPromoRepository.findById(id)).thenReturn(Optional.of(existingPromo));
        when(fixedAmountPromoRepository.save(any())).thenReturn(updatedPromo);

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Fixed Promo updated successfully!", responseEntity.getBody().getMessage());
    }

    @Test
    void testUpdate_PromoNotFound() {
        UUID id = UUID.randomUUID();
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(100L)
                .discountAmount(10L)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        when(fixedAmountPromoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Fixed Promo not found", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getPromo());
    }

    @Test
    void testUpdate_PromoFoundButNotValid() {
        UUID id = UUID.randomUUID();
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder()
                .name("Updated Promo")
                .description("This is an updated promo")
                .minimumPurchase(-50L)
                .discountAmount(10L)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        FixedAmountPromo existingPromo = new FixedAmountPromo();
        existingPromo.setId(id);
        existingPromo.setName("Existing Promo");
        existingPromo.setDescription("This is an existing promo");
        existingPromo.setMinimumPurchase(50L);
        existingPromo.setDiscountAmount(5L);
        existingPromo.setExpirationDate(LocalDateTime.now().plusDays(2));

        when(fixedAmountPromoRepository.findById(id)).thenReturn(Optional.of(existingPromo));

        ResponseEntity<PromoResponse> responseEntity = promoService.update(id, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Fixed Promo update failed! Invalid field(s)!", responseEntity.getBody().getMessage());
    }
}
