package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public interface FixedAmountPromoService {
    CompletableFuture<List<FixedAmountPromo>> findAll();
    CompletableFuture<ResponseEntity<PromoResponse>> create(FixedAmountPromoRequest request);
    CompletableFuture<ResponseEntity<PromoResponse>> update(UUID uuid, FixedAmountPromoRequest request);
    boolean isValid(FixedAmountPromo promo);
    boolean isNotExpired (FixedAmountPromo promo);
    boolean isNotNegativeMinPurchase (FixedAmountPromo promo);
    boolean isNotNegativeDiscount (FixedAmountPromo promo);
}
