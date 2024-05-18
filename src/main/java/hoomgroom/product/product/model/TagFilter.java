package hoomgroom.product.product.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());

        return filterNext(filteredProducts);
    }
}
