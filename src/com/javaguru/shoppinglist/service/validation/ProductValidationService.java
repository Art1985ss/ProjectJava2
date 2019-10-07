package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.util.Map;

public class ProductValidationService {
    private Map<String, ProductValidation> validations;

    public ProductValidationService(Map<String, ProductValidation> validations) {
        this.validations = validations;
    }

    public void validate(Product product) {
        validations.values().forEach(productValidation -> productValidation.validate(product));
    }

    public void nameValidation(String name) {
        ((ProductNameValidation) validations.get("Name")).nameValidation(name);
    }

}
