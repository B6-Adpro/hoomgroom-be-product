package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.FixedAmountPromo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FixedAmountPromoRepository extends JpaRepository<FixedAmountPromo, UUID> {
    Optional<FixedAmountPromo> findById(@NonNull UUID id);
    List<FixedAmountPromo> findByName(@NonNull String name);

    @Query("SELECT p FROM FixedAmountPromo p WHERE p.expirationDate <= :dateTime")
    List<FixedAmountPromo> findByExpirationDateBefore(@NonNull LocalDateTime dateTime);

    List<FixedAmountPromo> findByDiscountAmountBetween(Long lowerLimit, Long upperLimit);
}
