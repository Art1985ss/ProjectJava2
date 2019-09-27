package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.repository.Product;
import com.javaguru.shoppinglist.service.ProductService;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleCreateProduct {

    public void createProduct(ProductService productService, Scanner scanner) {
        String name;
        BigDecimal price;
        String category;
        BigDecimal discount;
        String description;
        Long id = -1L;
        do {
            try {
                scanner.nextLine();
                System.out.println("Enter product name : ");
                name = scanner.nextLine();
                System.out.println("Enter product price : ");
                price = new BigDecimal(scanner.nextLine());
                System.out.println("Enter category : ");
                category = scanner.nextLine();
                System.out.println("Enter discount : ");
                discount = new BigDecimal(scanner.nextLine());
                System.out.println("Enter description : ");
                description = scanner.nextLine();
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setCategory(category);
                product.setDiscount(discount);
                product.setDescription(description);
                id = productService.createProduct(product);
                System.out.println("New product id is : " + id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Options : ");
            System.out.println("1. Add another product.");
            System.out.println("2. Exit to main menu.");
            switch (scanner.nextInt()){
                case 1:
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Choose valid option 1 - 2.");
            }
        } while (true);
    }
}
