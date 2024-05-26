package hoomgroom.product.promo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;
    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    @Column(name = "minimum_purchase")
    Long minimumPurchase;

    public abstract Long applyPromo(Long totalPrice);
}
