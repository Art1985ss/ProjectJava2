package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.console.ConsoleCreateProduct;
import com.javaguru.shoppinglist.console.MenuConsole;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.validation.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class ShoppingListApplication {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        Set<ProductValidation> productValidationRules = new HashSet<>();
        productValidationRules.add(new ProductNameValidation());
        productValidationRules.add(new ProductPriceValidation());
        productValidationRules.add(new ProductDiscountValidation());
        ProductValidationService productValidationService = new ProductValidationService(productValidationRules);
        ProductService productService = new ProductService(productRepository, productValidationService);

        Scanner scanner = new Scanner(System.in);
        ConsoleCreateProduct consoleCreateProduct = new ConsoleCreateProduct(productService, scanner);
        MenuConsole console = new MenuConsole(productService, consoleCreateProduct, scanner);
        console.execute();
    }
}
