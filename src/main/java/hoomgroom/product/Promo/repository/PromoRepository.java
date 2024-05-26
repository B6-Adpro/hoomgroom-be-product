package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.Promo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PromoRepository extends JpaRepository<Promo, UUID> {
    Optional<Promo> findById(@NonNull UUID id);
}
