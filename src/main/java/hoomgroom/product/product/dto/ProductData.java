package hoomgroom.product.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductData {
    private String name;
    private List<String> tags;
    private String description;
    private String imageLink;
    private Long originalPrice;
    private Integer discountPercentage;
    private Integer totalSales;
}
