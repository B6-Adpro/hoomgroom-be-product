package hoomgroom.product.Product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @Column(name = "tags")
    private List<String> tags;

    @Column(name = "description")
    private String description;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "original_price")
    private Long originalPrice;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "total_sales")
    private Integer totalSales;

    public Long getDiscountedPrice() {
        Double discountMultiplier = (100 - this.discountPercentage) / 100.0;
        return (long) (this.originalPrice * (discountMultiplier));
    }
}