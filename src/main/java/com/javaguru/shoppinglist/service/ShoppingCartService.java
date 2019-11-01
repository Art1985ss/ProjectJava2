package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.service.validation.shoppingcart.ShoppingCartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public Long createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.add(shoppingCart)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart was not found in database.")).getId();
    }

    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with id " + id + " was not found."));
    }

    public ShoppingCart findByName(String name) {
        return shoppingCartRepository.findByName(name)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with name " + name + " was not found."));
    }

    public ShoppingCart delete(Long id) {
        return shoppingCartRepository.delete(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart didn't exist to delete."));
    }

    public List<Product> addProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCart.addProduct(product)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart product list is empty."));
    }

    public void showAllProductsFromShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.getProductList().forEach(System.out::println);
    }

    public BigDecimal getTotalPriceOfProductsFromShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCart.getPriceTotal()
                .orElseThrow(() -> new RuntimeException("Could not calculate total price for products in this shopping cart."));
    }

    public void saveChanges(ShoppingCart shoppingCart){
        shoppingCartRepository.update(shoppingCart)
                .orElseThrow(()-> new ShoppingCartNotFoundException("Shopping cart didn't exist to update."));
    }
}
