package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

public class ProductNameValidation implements ProductValidation {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 32;

    @Override
    public void validate(Product product) {
        checkNull(product);
        if (product.getName() == null) {
            throw new ProductValidationException("Product name should not be null.");
        }
        if (product.getName().length() < MIN_NAME_LENGTH || product.getName().length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException("Product name length can't be shorter than 3 and longer than 32 symbols.");
        }
    }
}
