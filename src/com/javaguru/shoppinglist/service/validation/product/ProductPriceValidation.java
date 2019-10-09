package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;

import java.math.BigDecimal;

public class ProductPriceValidation implements ProductValidation {
    @Override
    public void validate(Product product) {
        checkNull(product);
        checkPriceNotNull(product.getPrice());
        checkGreaterThanZero(product.getPrice());
    }

    private void checkPriceNotNull(BigDecimal price) {
        if (price == null) {
            throw new ProductValidationException("Product price should not be null.");
        }
    }

    private void checkGreaterThanZero(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) != 1) {
            throw new ProductValidationException("Product price should be greater than 0.");
        }
    }
}
