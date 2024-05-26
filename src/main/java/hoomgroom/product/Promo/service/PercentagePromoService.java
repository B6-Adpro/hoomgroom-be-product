package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.dto.PercentagePromoRequest;
import hoomgroom.product.Promo.model.PercentagePromo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PercentagePromoService {
    List<PercentagePromo> findAll();
    PercentagePromo findById(UUID uuid);
    PercentagePromo create(PercentagePromoRequest request);
    PercentagePromo update(UUID uuid, PercentagePromoRequest request);
    void delete(UUID uuid);
}
