package com.javaguru.shoppinglist.entity;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCartTest {
    private Product product;
    private ShoppingCart victim;

    @Before
    public void setUp() {
        victim = new ShoppingCart();
        victim.setName("Test Cart");
        product = product();
    }

    @Test
    public void getProductListTest() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        victim.addProduct(product);
        assertEquals(productList, victim.getProductList());
    }

    @Test
    public void addProductTest() {
        Product product1 = product();
        product1.setId(1L);
        assertEquals(product1, victim.addProduct(product1).get().get(0));
    }

    @Test
    public void getPriceTotal() {
        victim.addProduct(product);
        BigDecimal expected = new BigDecimal("95.00");
        BigDecimal actual = victim.getPriceTotal().get();
        assertEquals(expected, actual);
    }

    private Product product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(new BigDecimal("100"));
        product.setCategory("Fruit");
        product.setDiscount(new BigDecimal("5"));
        product.setDescription("This is apple for testing.");
        return product;
    }
}