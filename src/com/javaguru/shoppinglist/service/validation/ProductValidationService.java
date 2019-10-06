package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

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
