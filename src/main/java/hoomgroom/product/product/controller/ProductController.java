package hoomgroom.product.product.controller;

import hoomgroom.product.product.dto.ProductData;
import hoomgroom.product.product.model.Product;
import hoomgroom.product.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getFilteredProducts(
            @RequestParam(defaultValue = "") List<String> tags,
            @RequestParam(defaultValue = "0") Long minValue,
            @RequestParam(defaultValue = "" + Long.MAX_VALUE) Long maxValue,
            @RequestParam(defaultValue = "false") Boolean hasDiscount
    ) {
        List<Product> products = productService.findByFilter(tags, minValue, maxValue, hasDiscount);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.findById(UUID.fromString(id));
        return ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductData productData) {
        Product product = productService.create(productData);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody ProductData productData) {
        Product product = productService.update(UUID.fromString(id), productData);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.delete(UUID.fromString(id));
        return ResponseEntity.ok(String.format("Deleted product: %s", id));
    }
}
