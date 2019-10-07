package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.console.ConsoleCreateProduct;
import com.javaguru.shoppinglist.console.MenuConsole;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.validation.*;

import java.util.*;

class ShoppingListApplication {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        Map<String, ProductValidation> productValidationRules = new HashMap<>();
        productValidationRules.put("Name", new ProductNameValidation(productRepository));
        productValidationRules.put("Price", new ProductPriceValidation());
        productValidationRules.put("Discount", new ProductDiscountValidation());
        ProductValidationService productValidationService = new ProductValidationService(productValidationRules);
        ProductService productService = new ProductService(productRepository, productValidationService);

        Scanner scanner = new Scanner(System.in);
        ConsoleCreateProduct consoleCreateProduct = new ConsoleCreateProduct(productService, scanner);
        MenuConsole console = new MenuConsole(productService, consoleCreateProduct, scanner);
        console.execute();
    }
}
