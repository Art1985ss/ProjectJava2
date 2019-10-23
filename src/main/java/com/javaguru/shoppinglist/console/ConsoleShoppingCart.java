package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ConsoleShoppingCart {
    private Scanner scanner;
    private ShoppingCartService shoppingCartService;
    private ConsoleShoppingCartManagement consoleShoppingCartManagement;

    @Autowired
    public ConsoleShoppingCart(Scanner scanner,
                               ShoppingCartService shoppingCartService,
                               ConsoleShoppingCartManagement consoleShoppingCartManagement) {
        this.scanner = scanner;
        this.shoppingCartService = shoppingCartService;
        this.consoleShoppingCartManagement = consoleShoppingCartManagement;
    }

    public void execute() {
        while (true) {
            scanner.nextLine();
            try {
                System.out.println("Shopping cart management options : ");
                System.out.println("1. Create new shopping cart.");
                System.out.println("2. Find shopping cart by id.");
                System.out.println("3. Find shopping cart by name.");
                System.out.println("4. Delete shopping cart by id.");
                System.out.println("5. Manage shopping cart.");
                System.out.println("6. Return to main menu.");
                switch (scanner.nextInt()) {
                    case 1:
                        consoleShoppingCartManagement.createShoppingCart();
                        break;
                    case 2:
                        System.out.println("Please enter shopping cart id you wish to find : ");
                        System.out.println(shoppingCartService.findById(scanner.nextLong()));
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Please enter shopping cart name you wish to find : ");
                        System.out.println(shoppingCartService.findByName(scanner.nextLine()));
                        break;
                    case 4:
                        scanner.nextLine();
                        System.out.println("Please enter shopping cart id you wish to delete : ");
                        System.out.println(shoppingCartService.delete(scanner.nextLong()));
                        System.out.println("was deleted.");
                        break;
                    case 5:
                        System.out.println("Please enter shopping cart id you wish to manage : ");
                        consoleShoppingCartManagement.manageShoppingCart(shoppingCartService.findById(scanner.nextLong()));
                    case 6:
                        return;
                    default:
                        System.out.println("Please enter valid options (1 - 5).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter integer to choose valid option!");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
