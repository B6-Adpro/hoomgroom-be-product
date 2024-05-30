package hoomgroom.product.product.util;

import hoomgroom.product.product.model.Product;

import java.util.Collections;
import java.util.List;

public class TagFilter extends SearchFilter {
    List<String> tags;

    public TagFilter(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        if (tags.isEmpty()) {
            return filterNext(products);
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> !Collections.disjoint(product.getTags(), this.tags))
                .toList();

        return filterNext(filteredProducts);
    }
}
