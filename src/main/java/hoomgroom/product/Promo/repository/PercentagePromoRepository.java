package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.PercentagePromo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface PercentagePromoRepository extends JpaRepository<PercentagePromo, UUID> {
}