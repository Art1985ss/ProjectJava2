package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.math.BigDecimal;

public class ProductPriceValidation implements ProductValidation {
    @Override
    public void validate(Product product) {
        checkNull(product);
        if (product.getPrice() == null) {
            throw new ProductValidationException("Product price should not be null.");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) != 1) {
            throw new ProductValidationException("Product price should be greater than 0.");
        }
    }
}
