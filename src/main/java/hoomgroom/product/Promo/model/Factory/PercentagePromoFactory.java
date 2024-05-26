package hoomgroom.product.promo.model.factory;

import hoomgroom.product.promo.model.PercentagePromo;

public class PercentagePromoFactory extends PromoFactory {
    @Override
    public PercentagePromo createPromo() {
        return new PercentagePromo();
    }
}
