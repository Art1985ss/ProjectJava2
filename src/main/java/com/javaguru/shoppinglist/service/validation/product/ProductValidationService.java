package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import java.util.Set;

public class ProductValidationService {
    private Set<ProductValidation> validations;

    public ProductValidationService(Set<ProductValidation> validations) {
        this.validations = validations;
    }

    public void validate(Product product) {
        validations.forEach(productValidation -> productValidation.validate(product));
    }

}
