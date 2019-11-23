package com.javaguru.shoppinglist.console.console;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
@Profile({"console"})
public class ConsoleShoppingCartManagement {
    private Scanner scanner;
    private ShoppingCartService shoppingCartService;
    private ProductService productService;

    @Autowired
    public ConsoleShoppingCartManagement(Scanner scanner,
                                         ShoppingCartService shoppingCartService,
                                         ProductService productService) {
        this.scanner = scanner;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    public void createShoppingCart() {
        do {
            try {
                scanner.nextLine();
                System.out.println("Please enter name for your shopping cart : ");
                String name = scanner.nextLine();
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setName(name);
                System.out.println("New shopping cart created : ");
                System.out.println(shoppingCartService.createShoppingCart(shoppingCart));
                System.out.println("Options : ");
                System.out.println("1. Add another shopping cart.");
                System.out.println("2. Return to shopping cart management menu.");
                switch (scanner.nextInt()) {
                    case 1:
                        break;
                    case 2:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter integer to choose your options.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void manageShoppingCart(ShoppingCart shoppingCart) {
        while (true) {
            try {
                scanner.nextLine();
                System.out.println("Products currently in this cart : ");
                shoppingCartService.showAllProductsFromShoppingCart(shoppingCart);
                System.out.println("Options : ");
                System.out.println("1. Add product to your shopping cart.");
                System.out.println("2. Show total price for your products in shopping cart.");
                System.out.println("3. Save changes.");
                System.out.println("4. Return to shopping cart management menu.");
                switch (scanner.nextInt()) {
                    case 1:
                        this.addProducts(shoppingCart);
                        break;
                    case 2:
                        this.showTotalProductPrice(shoppingCart);
                        break;
                    case 3:
                        this.saveChanges(shoppingCart);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Please enter valid integer (1 - 4).");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addProducts(ShoppingCart shoppingCart) {
        System.out.println("Products available to add : ");
        productService.getAllProducts();
        System.out.println("Please enter product id you wish to add : ");
        shoppingCartService.addProduct(shoppingCart, productService.findById(scanner.nextLong()));
    }

    private void showTotalProductPrice(ShoppingCart shoppingCart) {
        System.out.println("Total price for all products in this shopping cart is : ");
        System.out.println(shoppingCartService.getTotalPriceOfProductsFromShoppingCart(shoppingCart));
    }

    private void saveChanges(ShoppingCart shoppingCart) {
        this.shoppingCartService.saveChanges(shoppingCart);
        System.out.println();
    }

}
