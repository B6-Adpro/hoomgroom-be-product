package hoomgroom.product.promo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PercentagePromoRequest {
    private String name;
    private String description;
    private LocalDateTime expirationDate;
    private Long minimumPurchase;
    private Double percentage;
}
