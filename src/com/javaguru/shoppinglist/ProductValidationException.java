package com.javaguru.shoppinglist;

public class ProductValidationException extends RuntimeException {
    public ProductValidationException(String message) {
        super(message);
    }
}
