package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public class ProductDiscountValidationTest {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100");
    private static final BigDecimal MIN_PRICE_FOR_DISCOUNT = new BigDecimal("20");
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ProductDiscountValidation victim;
    private Product testProduct;

    @Before
    public void setUp() {
        victim = new ProductDiscountValidation();
        testProduct = product();
    }

    @Test
    public void validateTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product discount should not be null");
        testProduct.setDiscount(null);
        victim.validate(testProduct);
    }

    @Test
    public void shouldReturnDiscountTooBig() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product discount can't be greater than " + MAX_DISCOUNT);
        testProduct.setDiscount(new BigDecimal("110"));
        victim.validate(testProduct);
    }

    @Test
    public void shouldReturnDiscountBelowZero() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product discount can't be less then 0");
        testProduct.setDiscount(new BigDecimal("-1"));
        victim.validate(testProduct);
    }

    @Test
    public void shouldNotBeAbleToSetDiscount() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product discount should be 0 for products with price lower than " + MIN_PRICE_FOR_DISCOUNT);
        testProduct.setPrice(new BigDecimal("5"));
        testProduct.setDiscount(new BigDecimal("2"));
        victim.validate(testProduct);
    }

    @Test
    public void shouldPassValidation() {
        testProduct.setDiscount(new BigDecimal("5"));
        victim.validate(testProduct);
    }

    private Product product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(new BigDecimal("32.20"));
        product.setCategory("Fruit");
        product.setDiscount(new BigDecimal("0"));
        product.setDescription("This is apple for testing.");
        return product;
    }
}