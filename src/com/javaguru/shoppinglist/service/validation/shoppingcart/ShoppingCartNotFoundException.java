package com.javaguru.shoppinglist.service.validation.shoppingcart;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
