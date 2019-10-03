package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.service.ProductService;

import java.util.Scanner;

public class MenuConsole {
    private ProductService productService;
    private ConsoleCreateProduct consoleCreateProduct;
    private Scanner scanner;

    public MenuConsole(ProductService productService,
                       ConsoleCreateProduct consoleCreateProduct,
                       Scanner scanner) {
        this.productService = productService;
        this.consoleCreateProduct = consoleCreateProduct;
        this.scanner = scanner;
    }

    public void execute() {
        while (true) {
            try {
                System.out.println("1. Create product.");
                System.out.println("2. Find product by id.");
                System.out.println("3. Display all products.");
                System.out.println("4. Exit.");
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("Product creation :");
                        consoleCreateProduct.createProduct();
                        break;
                    case 2:
                        System.out.print("Enter product id you wish to find :");
                        System.out.println(productService.findById(scanner.nextLong()));
                        break;
                    case 3:
                        productService.showProducts();
                        break;
                    case 4:
                        System.out.println("You have ended execution.");
                        return;
                    default:
                        System.out.println("Please choose valid option. (1 - 4)");
                }
            } catch (Exception e) {
                System.out.println("Error! Please try again!");
            }
        }
    }
}
