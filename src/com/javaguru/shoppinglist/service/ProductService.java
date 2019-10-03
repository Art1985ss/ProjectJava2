package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.repository.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.validation.ProductValidationService;

public class ProductService {
    private ProductRepository productRepository;
    private ProductValidationService productValidationService;

    public ProductService(ProductRepository productRepository,
                          ProductValidationService productValidationService) {
        this.productRepository = productRepository;
        this.productValidationService = productValidationService;
    }

    public Long createProduct(Product product) {
        productValidationService.validate(product);
        return productRepository.add(product).getId();
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public void showProducts() {
        System.out.println("Product list : ");
        productRepository.getAll().entrySet().forEach(System.out::println);
    }
}
