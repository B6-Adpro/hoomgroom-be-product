package hoomgroom.product.Promo.model.Factory;

import hoomgroom.product.Promo.model.FixedAmountPromo;

public class FixedAmountPromoFactory extends PromoFactory {
    @Override
    public FixedAmountPromo createPromo() {
        return new FixedAmountPromo();
    }
}
