package hoomgroom.product.promo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "percentage_promo")
@Getter
@Setter
public class PercentagePromo extends Promo {
    @Column(name = "percentage")
    Double percentage;

    @Override
    public Long applyPromo(Long totalPrice) {
        if (totalPrice >= minimumPurchase) {
            return (long) (100 - percentage) * totalPrice / 100;
        }
        return 0L;
    }
}
