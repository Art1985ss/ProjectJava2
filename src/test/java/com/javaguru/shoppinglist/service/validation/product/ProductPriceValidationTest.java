package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class ProductPriceValidationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ProductPriceValidation victim;
    private Product testProduct;
    private String exceptionMessage;

    @Before
    public void setUp() {
        victim = new ProductPriceValidation();
        testProduct = product();
    }

    @Test
    public void validateTest() {
        exceptionMessage = "Product price should not be null.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setPrice(null);
        victim.validate(testProduct);

        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldReturnPriceBelowZero() {
        exceptionMessage = "Product price should be greater than 0.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        Product testProduct = product();
        testProduct.setPrice(new BigDecimal("-10"));
        victim.validate(testProduct);

        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldPassValidation() {
        victim.validate(product());
    }

    private Product product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(new BigDecimal("1.20"));
        product.setCategory("Fruit");
        product.setDiscount(new BigDecimal("0"));
        product.setDescription("This is apple for testing.");
        return product;
    }
}