package hoomgroom.product.promo.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Generated
public class RedeemResponse {
    private String message;
    private UUID transactionId;
    private UUID promoId;
    private Long normalPrice;
    private Long discountPrice;
}
