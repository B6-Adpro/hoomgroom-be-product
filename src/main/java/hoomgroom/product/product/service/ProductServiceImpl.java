package hoomgroom.product.product.service;

import hoomgroom.product.product.dto.ProductData;
import hoomgroom.product.product.model.Product;
import hoomgroom.product.product.repository.ProductRepository;
import hoomgroom.product.product.util.DiscountFilter;
import hoomgroom.product.product.util.PriceFilter;
import hoomgroom.product.product.util.SearchFilter;
import hoomgroom.product.product.util.TagFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(ProductData productData) {
        Product product = Product.builder()
                .name(productData.getName())
                .tags(productData.getTags())
                .description(productData.getDescription())
                .imageLink(productData.getImageLink())
                .originalPrice(productData.getOriginalPrice())
                .discountPercentage(productData.getDiscountPercentage())
                .totalSales(0)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product update(UUID targetId, ProductData newData) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(targetId);

        if (product.isEmpty()) {
            throw new NoSuchElementException();
        }

        Product newProduct = Product.builder()
                .id(product.get().getId())
                .name(newData.getName())
                .tags(newData.getTags())
                .description(newData.getDescription())
                .imageLink(newData.getImageLink())
                .originalPrice(newData.getOriginalPrice())
                .discountPercentage(newData.getDiscountPercentage())
                .totalSales(product.get().getTotalSales())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public void delete(UUID targetId) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(targetId);

        if (product.isEmpty()) {
            throw new NoSuchElementException();
        }

        productRepository.delete(product.get());
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByFilter(List<String> tags, Long minValue, Long maxValue, Boolean maxDiscount) {
        SearchFilter searchFilter = SearchFilter.link(
                new TagFilter(tags),
                new PriceFilter(minValue, maxValue),
                new DiscountFilter(maxDiscount)
        );

        return searchFilter.filter(productRepository.findAll());
    }

    @Override
    public Product findById(UUID targetId) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(targetId);

        if (product.isEmpty()) {
            throw new NoSuchElementException();
        }

        return product.get();
    }
}
