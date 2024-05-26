package hoomgroom.product.promo.repository;

import hoomgroom.product.promo.model.Promo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromoRepository extends JpaRepository<Promo, UUID> {
    Optional<Promo> findById(@NonNull UUID id);
    List<Promo> findByName(@NonNull String name);
    @Query("SELECT p FROM Promo p WHERE p.expirationDate <= :dateTime")
    List<Promo> findByExpirationDateBefore(@NonNull LocalDateTime dateTime);
}
