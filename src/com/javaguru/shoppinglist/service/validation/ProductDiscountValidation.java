package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.math.BigDecimal;

public class ProductDiscountValidation implements ProductValidation {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100");

    @Override
    public void validate(Product product) {
        checkNull(product);
        checkDiscountNotNull(product.getDiscount());
        checkDiscountGreaterThan(product.getDiscount());
    }
    private void checkDiscountNotNull(BigDecimal discount){
        if (discount == null) {
            throw new ProductValidationException("Product discount should not be null");
        }
    }
    private void checkDiscountGreaterThan(BigDecimal discount){
        if (discount.compareTo(MAX_DISCOUNT) > 0) {
            throw new ProductValidationException("Product discount can't be greater than 100");
        }
    }
}
