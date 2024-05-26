package hoomgroom.product.promo.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Generated
public class RedeemPromoRequest {
    private UUID transactionId;
    private Long totalPrice;
}