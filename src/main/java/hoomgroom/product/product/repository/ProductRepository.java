package hoomgroom.product.product.repository;

import hoomgroom.product.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }

        productData.add(product);
        return product;
    }

    public Product update(String targetId, Product newProduct) {
        for (int i = 0; i < productData.size(); i++) {
            Product product = productData.get(i);
            if (product.getId().equals(targetId)) {
                productData.remove(product);
                productData.add(i, newProduct);
                return newProduct;
            }
        }

        return null;
    }

    public void delete(String targetId) {
        productData.remove(findById(targetId));
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    public Product findById(String targetId) {
        for (Product product : productData) {
            if (product.getId().equals(targetId)) {
                return product;
            }
        }

        return null;
    }
}
