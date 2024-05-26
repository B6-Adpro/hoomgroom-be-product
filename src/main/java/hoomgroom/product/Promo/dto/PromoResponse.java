package hoomgroom.product.promo.dto;

import hoomgroom.product.promo.model.Promo;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Getter
@Builder
@Generated
public class PromoResponse {
    private String message;
    private Promo promo;
}
