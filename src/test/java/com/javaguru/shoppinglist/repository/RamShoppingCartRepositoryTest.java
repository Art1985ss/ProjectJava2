package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.repository.shoppingcart.RamShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(testCart);
        victim.save(testCart);
        assertEquals(shoppingCartList, victim.findAll().get());
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