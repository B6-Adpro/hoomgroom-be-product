package hoomgroom.product.product.service;

import hoomgroom.product.product.dto.ProductData;
import hoomgroom.product.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product create(ProductData productData);
    Product update(UUID targetId, ProductData newData);
    void delete(UUID targetId);
    List<Product> findAll();
    Product findById(UUID targetId);
}
