package hoomgroom.product.Promo.model.Factory;

import hoomgroom.product.Promo.model.PercentagePromo;

public class PercentagePromoFactory extends PromoFactory {
    @Override
    public PercentagePromo createPromo() {
        return new PercentagePromo();
    }
}
