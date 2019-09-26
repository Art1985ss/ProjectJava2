package com.javaguru.shoppinglist;

import java.math.BigDecimal;

public class ProductValidationService {
    public static boolean validate(Product product){
        if (product.getPrice().compareTo( new BigDecimal(0)) == -1){
            throw new ProductValidationException("Product price should be greater than 0!");
        }
        if (product.getDiscount() == null){
            product.setDiscount(new BigDecimal("0"));
        }else if (product.getDiscount().compareTo(new BigDecimal(100)) == 1){
            throw new ProductValidationException("Product discount can't be greater than 100!");
        }
        if (product.getName().length() < 3 ||  product.getName().length() > 32){
            throw new ProductValidationException("Product name can't be shorter than 3 or longer than 32 symbols!");
        }
        return true;
    }

}
