package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.math.BigDecimal;

public class ProductDiscountValidation implements ProductValidation {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100");

    @Override
    public void validate(Product product) {
        checkNull(product);
        if (product.getDiscount() == null) {
            throw new ProductValidationException("Product discount should not be null");
        }
        if (product.getDiscount().compareTo(MAX_DISCOUNT) == 1) {
            throw new ProductValidationException("Product discount can't be greater than 100");
        }
    }
}
