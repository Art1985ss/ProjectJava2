package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDiscountValidationTest {
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100");
    private static final BigDecimal MIN_PRICE_FOR_DISCOUNT = new BigDecimal("20");
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ProductDiscountValidation victim;
    private Product testProduct;
    private String exceptionMessage;

    @Before
    public void setUp() {
        victim = new ProductDiscountValidation();
        testProduct = product();
    }

    @Test
    public void validateTest() {
        exceptionMessage = "Product discount should not be null";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setDiscount(null);
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldReturnDiscountTooBig() {
        exceptionMessage = "Product discount can't be greater than " + MAX_DISCOUNT;
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setDiscount(new BigDecimal("110"));
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldReturnDiscountBelowZero() {
        exceptionMessage = "Product discount can't be less then 0";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setDiscount(new BigDecimal("-1"));
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldNotBeAbleToSetDiscount() {
        exceptionMessage = "Product discount should be 0 for products with price lower than " + MIN_PRICE_FOR_DISCOUNT;
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setPrice(new BigDecimal("5"));
        testProduct.setDiscount(new BigDecimal("2"));
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
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