package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PercentagePromoService {
    ResponseEntity<List<PercentagePromo>> findAll();
    ResponseEntity<PromoResponse> create(PercentagePromoRequest request);
    ResponseEntity<PromoResponse> update(UUID uuid, PercentagePromoRequest request);
    boolean isValid(PercentagePromo promo);
    boolean isNotExpired (PercentagePromo promo);
    boolean isNotNegativeMinPurchase (PercentagePromo promo);
    boolean isNotNegativeDiscount (PercentagePromo promo);
}
