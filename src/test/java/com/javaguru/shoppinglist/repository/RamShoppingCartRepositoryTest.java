package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.repository.shoppingcart.RamShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RamShoppingCartRepositoryTest {
    private RamShoppingCartRepository victim;
    private ShoppingCart testCart;

    @Before
    public void setUp() {
        victim = new RamShoppingCartRepository();
        testCart = shoppingCart();
    }

    @Test
    public void add() {
        assertEquals(testCart, victim.save(testCart).get());
    }

    @Test
    public void findById() {
        victim.save(testCart);
        assertEquals(testCart, victim.findById(testCart.getId()).get());
    }

    @Test
    public void findByName() {
        victim.save(testCart);
        assertEquals(testCart, victim.findByName(testCart.getName()).get());
    }

    @Test
    public void getAll() {
        Map<Long, ShoppingCart> productMap = new HashMap<>();
        productMap.put(0L, testCart);
        victim.save(testCart);
        assertEquals(productMap, victim.findAll());
    }

    @Test
    public void delete() {
        victim.save(testCart);
        assertEquals(testCart, victim.delete(testCart.getId()).get());
    }

    private ShoppingCart shoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setName("Cart1");
        return shoppingCart;
    }
}