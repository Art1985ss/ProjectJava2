package com.javaguru.shoppinglist;

import java.math.BigDecimal;

public class ProductValidationService {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal(100);
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 32;

    public void validate(Product product) {
        if (product.getPrice() == null) {
            throw new ProductValidationException("Product should have price!");
        } else if (product.getPrice().compareTo(BigDecimal.ZERO) != 1) {
            throw new ProductValidationException("Product price should be greater than 0!");
        }

        if (product.getDiscount() != null) {
            if (product.getDiscount().compareTo(MAX_DISCOUNT) == 1) {
                throw new ProductValidationException("Product discount can't be greater than 100!");
            }
        }
        if (product.getName() == null) {
            throw new ProductValidationException("Product should have name!");
        } else if (product.getName().length() < MIN_NAME_LENGTH || product.getName().length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException("Product name can't be shorter than 3 or longer than 32 symbols!");
        }
    }

}
