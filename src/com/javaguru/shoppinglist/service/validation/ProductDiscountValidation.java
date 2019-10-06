package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.math.BigDecimal;

public class ProductDiscountValidation implements ProductValidation {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100");
    private static final BigDecimal MIN_PRICE_FOR_DISCOUNT = new BigDecimal("20");

    @Override
    public void validate(Product product) {
        checkNull(product);
        checkDiscountNotNull(product.getDiscount());
        checkDiscountGreaterThan(product.getDiscount());
        checkDiscountGraterThanZero(product.getDiscount());
        checkMinPriceForDiscount(product);
    }

    private void checkDiscountNotNull(BigDecimal discount) {
        if (discount == null) {
            throw new ProductValidationException("Product discount should not be null");
        }
    }

    private void checkDiscountGreaterThan(BigDecimal discount) {
        if (discount.compareTo(MAX_DISCOUNT) > 0) {
            throw new ProductValidationException("Product discount can't be greater than 100");
        }
    }

    private void checkDiscountGraterThanZero(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductValidationException("Product discount can't be less then 0");
        }
    }

    private void checkMinPriceForDiscount(Product product) {
        if (product.getPrice().compareTo(MIN_PRICE_FOR_DISCOUNT) < 0 &&
                product.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
            throw new ProductValidationException("Product discount should be 0 for products with price lower than 20.");
        }
    }
}
