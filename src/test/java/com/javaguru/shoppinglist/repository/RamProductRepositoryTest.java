package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.product.RamProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RamProductRepositoryTest {
    private RamProductRepository victim;
    private Product testProduct;

    @Before
    public void setUp() {
        victim = new RamProductRepository();
        testProduct = product();
    }

    @Test
    public void add() {
        assertEquals(testProduct, victim.save(testProduct).get());
    }

    @Test
    public void findById() {
        victim.save(testProduct);
        assertEquals(testProduct, victim.findById(testProduct.getId()).get());
    }

    @Test
    public void findByName() {
        victim.save(testProduct);
        assertEquals(testProduct, victim.findByName(testProduct.getName()).get());
    }

    @Test
    public void getAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        victim.save(testProduct);
        assertEquals(productList, victim.findAll().get());
    }

    @Test
    public void delete() {
        victim.save(testProduct);
        assertEquals(testProduct, victim.delete(testProduct.getId()).get());
    }

    private Product product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(new BigDecimal("32.20"));
        product.setCategory("Fruit");
        product.setDiscount(new BigDecimal("5"));
        product.setDescription("This is apple for testing.");
        return product;
    }
}