package hoomgroom.product.Product.repository;

import hoomgroom.product.Product.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findById(@NonNull UUID id);
}
