package hoomgroom.product.product.util;

import hoomgroom.product.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountFilter extends SearchFilter {
    Boolean hasDiscount;

    public DiscountFilter(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        if (!hasDiscount) {
            return filterNext(products);
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getDiscountPercentage() != 0)
                .collect(Collectors.toList());

        return filterNext(filteredProducts);
    }
}
