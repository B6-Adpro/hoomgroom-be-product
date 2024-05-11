package hoomgroom.product.product.service;

import hoomgroom.product.product.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product update(String targetId, Product newProduct);
    void delete(String targetId);
    List<Product> findAll();
    Product findById(String targetId);
}
