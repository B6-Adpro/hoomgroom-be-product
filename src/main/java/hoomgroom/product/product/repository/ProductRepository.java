package hoomgroom.product.product.repository;

import hoomgroom.product.product.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findById(@NonNull UUID id);
    List<Product> findByName(@NonNull String name);

}
