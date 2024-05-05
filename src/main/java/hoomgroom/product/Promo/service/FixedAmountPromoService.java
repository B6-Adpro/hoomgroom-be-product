package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.Promo.model.FixedAmountPromo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface FixedAmountPromoService {
    List<FixedAmountPromo> findAll();
    FixedAmountPromo findById(UUID uuid);
    FixedAmountPromo create(FixedAmountPromoRequest request);
    FixedAmountPromo update(UUID uuid, FixedAmountPromoRequest request);
    void delete(UUID uuid);
}
