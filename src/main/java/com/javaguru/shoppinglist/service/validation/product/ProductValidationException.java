package com.javaguru.shoppinglist.service.validation.product;

public class ProductValidationException extends RuntimeException {
    public ProductValidationException(String message) {
        super(message);
    }
}
