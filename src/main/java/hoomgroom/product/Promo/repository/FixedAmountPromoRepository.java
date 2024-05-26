package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.FixedAmountPromo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FixedAmountPromoRepository extends JpaRepository<FixedAmountPromo, UUID> {
}
