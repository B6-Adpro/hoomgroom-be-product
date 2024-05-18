package hoomgroom.product.product.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter extends SearchFilter {
    Long minPrice;
    Long maxPrice;

    public PriceFilter(Long minPrice, Long maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        if (minPrice == 0 && maxPrice == Long.MAX_VALUE) {
            return filterNext(products);
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> {
                    Long price = product.getDiscountedPrice();
                    return price >= minPrice && price <= maxPrice;
                })
                .collect(Collectors.toList());

        return filterNext(filteredProducts);
    }
}
