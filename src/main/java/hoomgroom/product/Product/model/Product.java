package hoomgroom.product.Product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private List<String> tags;
    private String description;
    private String imageLink;
    private Long originalPrice;
    private Integer discountPercentage;
    private Integer totalSales;

    public Long getDiscountedPrice() {
        Double discountMultiplier = (100 - this.discountPercentage) / 100.0;
        return (long) (this.originalPrice * (discountMultiplier));
    }
}