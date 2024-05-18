package hoomgroom.product.product.model;

import java.util.List;

public abstract class SearchFilter {
    private SearchFilter next;

    public static SearchFilter link(SearchFilter first, SearchFilter... chain) {
        SearchFilter head = first;
        for (SearchFilter nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract List<Product> filter(List<Product> products);
    protected List<Product> filterNext(List<Product> products) {
        if (next == null) {
            return products;
        }
        return next.filter(products);
    }
}
