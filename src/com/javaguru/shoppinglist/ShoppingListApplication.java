package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.console.MenuConsole;
import com.javaguru.shoppinglist.repository.Product;
import com.javaguru.shoppinglist.service.validation.ProductValidationException;
import com.javaguru.shoppinglist.service.validation.ProductValidationService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ShoppingListApplication {

    public static void main(String[] args) {
        MenuConsole console = new MenuConsole();
        console.execute();
    }
}
