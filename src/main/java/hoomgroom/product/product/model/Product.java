package hoomgroom.product.product.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Product {
    private UUID id;
    private String name;
    private ArrayList<String> tags;
    private String description;
    private String imageLink;
    private Long originalPrice;
    private Integer discountPercentage;
    private Integer totalSales;
}
