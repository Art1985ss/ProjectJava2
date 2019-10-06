package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;

public class ProductNameValidation implements ProductValidation {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 32;
    private ProductRepository productRepository;

    public ProductNameValidation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validate(Product product) {
        checkNull(product);
        checkNameNotNull(product.getName());
        checkNameMinLength(product.getName());
        checkNameMaxLength(product.getName());
        checkNameIsUnique(product.getName());
    }

    private void checkNameNotNull(String name) {
        if (name == null) {
            throw new ProductValidationException("Product name should not be null.");
        }
    }

    private void checkNameMinLength(String name) {
        if (name.length() < MIN_NAME_LENGTH) {
            throw new ProductValidationException("Product name length can't be shorter than 3 symbols.");
        }
    }

    private void checkNameMaxLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException("Product name length can't be longer than 32 symbols.");
        }
    }

    private void checkNameIsUnique(String name) {
        if (productRepository.findByName(name) != null) {
            throw new ProductValidationException("Product with such name already exists in the database.");
        }
    }
}
