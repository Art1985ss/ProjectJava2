package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.validation.product.ProductNotFoundException;
import com.javaguru.shoppinglist.service.validation.product.ProductValidationService;

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
        return productRepository.add(product)
                .orElseThrow(()-> new ProductNotFoundException("Product was not found.")).getId();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product with id " + id + " was not found."));
    }

    public void showProducts() {
        System.out.println("Product list : ");
        productRepository.getAll().entrySet().forEach(System.out::println);
    }

    public Product findByName(String name) {
        return productRepository
                .findByName(name).orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found."));
    }
}
