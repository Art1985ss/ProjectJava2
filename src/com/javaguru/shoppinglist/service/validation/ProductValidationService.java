package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.repository.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductValidationService {
    private Set<ProductValidation> validations = new HashSet<>();

    public ProductValidationService() {
        validations.add(new ProductNameValidation());
        validations.add(new ProductPriceValidation());
        validations.add(new ProductDiscountValidation());
    }

    public void validate(Product product) {
        validations.forEach(productValidation -> productValidation.validate(product));
    }

}
