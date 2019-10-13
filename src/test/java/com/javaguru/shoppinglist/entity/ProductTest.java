package com.javaguru.shoppinglist.entity;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductTest {
    private Product victim;

    @Before
    public void setUp() throws Exception {
        victim = new Product();
        victim.setId(0L);
        victim.setName("Apple");
        victim.setPrice(new BigDecimal("100"));
        victim.setDiscount(new BigDecimal("5"));
        victim.setCategory("Fruit");
        victim.setDescription("This is for test.");
    }

    @Test
    public void getTotalPriceTest() {
        BigDecimal expected = new BigDecimal("95.00");
        assertEquals(expected, victim.getTotalPrice());
    }
}