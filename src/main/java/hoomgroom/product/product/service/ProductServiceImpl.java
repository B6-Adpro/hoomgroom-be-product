package hoomgroom.product.product.service;

import hoomgroom.product.product.model.Product;
import hoomgroom.product.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public Product update(String targetId, Product newProduct) {
        productRepository.update(targetId, newProduct);
        return newProduct;
    }

    @Override
    public void delete(String targetId) {
        productRepository.delete(targetId);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String targetId) {
        return productRepository.findById(targetId);
    }
}