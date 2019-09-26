package com.javaguru.shoppinglist;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ShoppingListApplication {

    public static void main(String[] args) {
        Map<Long, Product> productRepository = new HashMap<>();
        Long productIdSequence = 0L;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("1. Create product");
                System.out.println("2. Find product by id");
                System.out.println("3. Exit");
                Integer userInput = Integer.valueOf(scanner.nextLine());
                switch (userInput) {
                    case 1:
                        System.out.println("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter product price: ");
                        BigDecimal price = new BigDecimal(scanner.nextLine());
                        Product product = new Product();
                        product.setName(name);
                        product.setPrice(price);
                        if (ProductValidationService.validate(product)){
                            product.setId(productIdSequence);
                            productRepository.put(productIdSequence, product);
                            productIdSequence++;
                            System.out.println("Result: " + product.getId());
                        }
                        else {
                            return;
                        }
                    case 2:
                        System.out.println("Enter product id: ");
                        long id = scanner.nextLong();
                        Product findProductResult = productRepository.get(id);
                        System.out.println(findProductResult);
                    case 3:
                        return;
                }
            }catch (ProductValidationException productException){
                System.out.println(productException.getMessage());
                System.out.println("Please insert valid data!");
            }
            catch (Exception e) {
                System.out.println("Error! Please try again.");
            }
        }
    }
}
