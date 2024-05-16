package hoomgroom.product.product.service;

import hoomgroom.product.product.dto.ProductData;
import hoomgroom.product.product.model.Product;

import java.util.List;

public interface ProductService {
    Product create(ProductData productData);
    Product update(String targetId, ProductData newData);
    void delete(String targetId);
    List<Product> findAll();
    Product findById(String targetId);
}
