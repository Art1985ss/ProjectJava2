package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductValidationService {
    private Set<ProductValidation> validations;

    @Autowired
    public ProductValidationService(Set<ProductValidation> validations) {
        this.validations = validations;
    }

    public void validate(Product product) {
        validations.forEach(productValidation -> productValidation.validate(product));
    }

}
