package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.product.ProductRepository;
import com.javaguru.shoppinglist.service.validation.product.ProductNotFoundException;
import com.javaguru.shoppinglist.service.validation.product.ProductValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ProductValidationService productValidationService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductValidationService productValidationService) {
        this.productRepository = productRepository;
        this.productValidationService = productValidationService;
    }

    @Transactional
    public Long createProduct(Product product) {
        productValidationService.validate(product);
        return productRepository.save(product)
                .orElseThrow(() -> new ProductNotFoundException("Product was not found.")).getId();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " was not found."));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().orElseThrow(() -> new ProductNotFoundException("No products in repository."));
    }

    public Product findByName(String name) {
        return productRepository
                .findByName(name).orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found."));
    }

    public Product deleteProductById(Long id){
        return productRepository.delete(id)
                .orElseThrow(() -> new ProductNotFoundException("No such product to delete."));
    }
}
