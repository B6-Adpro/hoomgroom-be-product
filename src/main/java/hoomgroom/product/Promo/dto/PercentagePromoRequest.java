package hoomgroom.product.promo.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Generated
public class PercentagePromoRequest {
    private String name;
    private String description;
    private LocalDateTime expirationDate;
    private Long minimumPurchase;
    private Double percentage;
}
