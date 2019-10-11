package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.console.ConsoleCreateProduct;
import com.javaguru.shoppinglist.console.ConsoleShoppingCart;
import com.javaguru.shoppinglist.console.ConsoleShoppingCartManagement;
import com.javaguru.shoppinglist.console.MenuConsole;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import com.javaguru.shoppinglist.service.validation.product.*;

import java.util.*;

class ShoppingListApplication {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        Set<ProductValidation> productValidationRules = new HashSet<>();
        productValidationRules.add(new ProductNameValidation(productRepository));
        productValidationRules.add(new ProductPriceValidation());
        productValidationRules.add(new ProductDiscountValidation());
        ProductValidationService productValidationService = new ProductValidationService(productValidationRules);
        ProductService productService = new ProductService(productRepository, productValidationService);
        ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository();
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository);
        Scanner scanner = new Scanner(System.in);
        ConsoleShoppingCartManagement consoleShoppingCartManagement = new ConsoleShoppingCartManagement(scanner, shoppingCartService, productService);
        ConsoleShoppingCart consoleShoppingCart = new ConsoleShoppingCart(scanner, shoppingCartService, consoleShoppingCartManagement);
        ConsoleCreateProduct consoleCreateProduct = new ConsoleCreateProduct(productService, scanner);
        MenuConsole console = new MenuConsole(productService, consoleCreateProduct, consoleShoppingCart, scanner);
        console.execute();
    }
}
