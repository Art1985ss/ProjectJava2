package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;


public class ProductPriceValidationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ProductPriceValidation victim;

    @Before
    public void setUp() {
        victim = new ProductPriceValidation();
    }

    @Test
    public void validateTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product price should not be null.");
        Product testProduct = product();
        testProduct.setPrice(null);
        victim.validate(testProduct);
    }

    @Test
    public void shouldReturnPriceBelowZero() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product price should be greater than 0.");
        Product testProduct = product();
        testProduct.setPrice(new BigDecimal("-10"));
        victim.validate(testProduct);
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