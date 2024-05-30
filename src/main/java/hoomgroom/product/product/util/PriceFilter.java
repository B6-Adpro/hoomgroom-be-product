package hoomgroom.product.product.util;

import hoomgroom.product.product.model.Product;

import java.util.List;

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
                .toList();

        return filterNext(filteredProducts);
    }
}
