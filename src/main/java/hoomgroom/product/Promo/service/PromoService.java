package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.model.Promo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PromoService {
    List<Promo> findAll();
    Promo findById(UUID uuid);
}
