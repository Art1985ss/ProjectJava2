package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductValidation {
    void validate(Product product);

    default void checkNull(Product product) {
        if (product == null) {
            throw new ProductValidationException("Product should not be null.");
        }
    }
}
