package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.repository.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.validation.ProductValidationService;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();
    private ProductValidationService productValidationService = new ProductValidationService();

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
