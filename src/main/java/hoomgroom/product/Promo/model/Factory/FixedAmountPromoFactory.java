package hoomgroom.product.promo.model.factory;

import hoomgroom.product.promo.model.FixedAmountPromo;

public class FixedAmountPromoFactory extends PromoFactory {
    @Override
    public FixedAmountPromo createPromo() {
        return new FixedAmountPromo();
    }
}
