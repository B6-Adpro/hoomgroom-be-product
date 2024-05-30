package hoomgroom.product.product.util;

import hoomgroom.product.product.model.Product;

import java.util.List;

public class DiscountFilter extends SearchFilter {
    Boolean hasDiscount;

    public DiscountFilter(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        if (Boolean.FALSE.equals(hasDiscount)) {
            return filterNext(products);
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getDiscountPercentage() != 0)
                .toList();

        return filterNext(filteredProducts);
    }
}
