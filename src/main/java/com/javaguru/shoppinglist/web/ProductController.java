package com.javaguru.shoppinglist.web;

import com.javaguru.shoppinglist.dto.EntityTransformer;
import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList = productService.getAllProducts().stream()
                .map(EntityTransformer::transformToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = EntityTransformer.transformFromDTO(productDTO);
        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = EntityTransformer.transformToDTO(productService.findById(id));
        return new ResponseEntity<>(productDTO, HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<ProductDTO> findByName(@RequestParam String name) {
        ProductDTO productDTO = EntityTransformer.transformToDTO(productService.findByName(name));
        return new ResponseEntity<>(productDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
