package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.dto.RedeemResponse;
import hoomgroom.product.promo.dto.Response;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.model.Promo;
import hoomgroom.product.promo.model.factory.FixedAmountPromoFactory;
import hoomgroom.product.promo.model.factory.PromoFactory;
import hoomgroom.product.promo.repository.PromoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoServiceImplTest {

    private PromoServiceImpl promoService;

    @Mock
    private PromoRepository promoRepository;

    private FixedAmountPromoFactory promoFactory = new FixedAmountPromoFactory();
    private Promo promo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        promoService = new PromoServiceImpl(promoRepository);
        promo = promoFactory.createPromo();
        promo.setName("promo");
        promo.setDescription("desc");
        promo.setMinimumPurchase(50000L);
        promo.setExpirationDate(LocalDateTime.now().plusHours(1));
    }

    @Test
    void testFindAll() {
        List<Promo> promoList = List.of(promo);

        when(promoRepository.findAll()).thenReturn(promoList);

        ResponseEntity<List<Promo>> responseEntity = promoService.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(promoList, responseEntity.getBody());
    }

    @Test
    void testFindById_PromoFound() {
        UUID promoId = UUID.randomUUID();
        promo.setId(promoId);

        when(promoRepository.findById(promoId)).thenReturn(Optional.of(promo));

        ResponseEntity<PromoResponse> responseEntity = promoService.findById(promoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(promo, Objects.requireNonNull(responseEntity.getBody()).getPromo());
    }

    @Test
    void testFindByIdPromoNotFound() {
        UUID promoId = UUID.randomUUID();

        when(promoRepository.findById(promoId)).thenReturn(Optional.empty());

        ResponseEntity<PromoResponse> responseEntity = promoService.findById(promoId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Promo id " + promoId + " not found!", responseEntity.getBody().getMessage());
    }

    @Test
    void testFindByIdNullIdThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            promoService.findById(null);
        });
    }

    @Test
    void testIsValidValidPromo() {
        LocalDateTime futureExpiration = LocalDateTime.now().plusDays(1);
        promo.setExpirationDate(futureExpiration);
        assertTrue(promoService.isValid(promo));
    }

    @Test
    void testIsValidExpiredPromo() {
        LocalDateTime pastExpiration = LocalDateTime.now().minusDays(1);
        Promo expiredPromo = promo;
        expiredPromo.setExpirationDate(pastExpiration);

        assertFalse(promoService.isValid(expiredPromo));
    }

    @Test
    void testDeletePromoNotFound() {
        UUID promoId = UUID.randomUUID();

        when(promoRepository.findById(promoId)).thenReturn(Optional.empty());

        ResponseEntity<Response> responseEntity = promoService.delete(promoId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Promo id " + promoId + " not found!", responseEntity.getBody().getMessage());
    }

    @Test
    void testDelete_PromoFound() {
        UUID promoId = UUID.randomUUID();
        promo.setId(promoId);

        when(promoRepository.findById(promoId)).thenReturn(Optional.of(promo));

        ResponseEntity<Response> responseEntity = promoService.delete(promoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Promo id " + promoId + " deleted successfully", responseEntity.getBody().getMessage());
        verify(promoRepository, times(1)).delete(promo);
    }

    @Test
    void testRedeem_PromoNotFound() {
        UUID promoId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Long totalPrice = 100L;

        when(promoRepository.findById(promoId)).thenReturn(Optional.empty());

        ResponseEntity<RedeemResponse> responseEntity = promoService.redeem(transactionId, promoId, totalPrice);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Promo id " + promoId + " not found!", responseEntity.getBody().getMessage());
    }

    @Test
    void testRedeem_PromoExpired() {
        UUID promoId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Long totalPrice = 100L;
        Promo expiredPromo = promo;
        expiredPromo.setId(promoId);
        expiredPromo.setExpirationDate(LocalDateTime.now().minusDays(1)); // Set expiration date in the past

        when(promoRepository.findById(promoId)).thenReturn(Optional.of(expiredPromo));

        ResponseEntity<RedeemResponse> responseEntity = promoService.redeem(transactionId, promoId, totalPrice);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Promo id " + promoId + " has expired!", responseEntity.getBody().getMessage());
    }

    @Test
    void testRedeem_TotalPriceNotHigherThanMinimumPurchase() {
        UUID promoId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Long totalPrice = 50L;
        Promo validPromo = promo;
        validPromo.setId(promoId);
        validPromo.setExpirationDate(LocalDateTime.now().plusDays(1)); // Set expiration date in the future
        validPromo.setMinimumPurchase(100L); // Set minimum purchase price higher than total price

        when(promoRepository.findById(promoId)).thenReturn(Optional.of(validPromo));

        ResponseEntity<RedeemResponse> responseEntity = promoService.redeem(transactionId, promoId, totalPrice);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Minimum Purchase not enough!", responseEntity.getBody().getMessage());
    }

    @Test
    void testRedeem_SuccessfulRedeem() {
        UUID promoId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Long totalPrice = 150L;
        Long discountPrice = 140L;
        FixedAmountPromo validPromo = new FixedAmountPromo();
        validPromo.setId(promoId);
        validPromo.setExpirationDate(LocalDateTime.now().plusDays(1));
        validPromo.setMinimumPurchase(100L);
        validPromo.setDiscountAmount(10L);

        when(promoRepository.findById(promoId)).thenReturn(Optional.of(validPromo));

        ResponseEntity<RedeemResponse> responseEntity = promoService.redeem(transactionId, promoId, totalPrice);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(promoId, responseEntity.getBody().getPromoId());
        assertEquals(transactionId, responseEntity.getBody().getTransactionId());
        assertEquals(totalPrice, responseEntity.getBody().getNormalPrice());
        assertEquals(discountPrice, responseEntity.getBody().getDiscountPrice());
    }
}
