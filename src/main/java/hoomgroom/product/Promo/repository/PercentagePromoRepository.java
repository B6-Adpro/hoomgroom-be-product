package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.PercentagePromo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PercentagePromoRepository extends JpaRepository<PercentagePromo, UUID> {
    Optional<PercentagePromo> findById(@NonNull UUID id);
    List<PercentagePromo> findByName(@NonNull String name);


    @Query("SELECT p FROM PercentagePromo p WHERE p.expirationDate <= :dateTime")
    List<PercentagePromo> findByExpirationDateBefore(@NonNull LocalDateTime dateTime);

    List<PercentagePromo> findByPercentageBetween(Double lowerLimit, Double upperLimit);
}