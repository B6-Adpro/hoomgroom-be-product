package hoomgroom.product.Promo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fixed_amount_promo")
@Getter
@Setter
public class FixedAmountPromo extends Promo {
    @Column(name = "discount_amount")
    Long discountAmount;
}
