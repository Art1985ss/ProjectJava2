package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ShoppingCartRepositoryTest {
    private ShoppingCartRepository victim;
    private ShoppingCart testCart;

    @Before
    public void setUp() {
        victim = new ShoppingCartRepository();
        testCart = shoppingCart();
    }

    @Test
    public void add() {
        assertEquals(testCart, victim.add(testCart).get());
    }

    @Test
    public void findById() {
        victim.add(testCart);
        assertEquals(testCart, victim.findById(testCart.getId()).get());
    }

    @Test
    public void findByName() {
        victim.add(testCart);
        assertEquals(testCart, victim.findByName(testCart.getName()).get());
    }

    @Test
    public void getAll() {
        Map<Long, ShoppingCart> productMap = new HashMap<>();
        productMap.put(0L, testCart);
        victim.add(testCart);
        assertEquals(productMap, victim.getAll());
    }

    @Test
    public void delete() {
        victim.add(testCart);
        assertEquals(testCart, victim.delete(testCart.getId()).get());
    }

    private ShoppingCart shoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setName("Cart1");
        return shoppingCart;
    }
}