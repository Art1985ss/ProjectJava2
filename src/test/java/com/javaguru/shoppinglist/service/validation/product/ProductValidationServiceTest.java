package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductValidationServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Spy
    ProductRepository productRepository;
    @Spy
    private ProductNameValidation productNameValidation;
    @Spy
    private ProductPriceValidation productPriceValidation;
    @Spy
    private ProductDiscountValidation productDiscountValidation;
    @Spy
    private Set<ProductValidation> validations;
    private ProductValidationService victim;
    private Product testProduct;
    @Captor
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    @Before
    public void setUp() {
        productRepository = new ProductRepository();
        validations = new HashSet<>();
        productNameValidation = new ProductNameValidation(productRepository);
        validations.add(productNameValidation);
        productPriceValidation = new ProductPriceValidation();
        validations.add(productPriceValidation);
        productDiscountValidation = new ProductDiscountValidation();
        validations.add(productDiscountValidation);
        victim = new ProductValidationService(validations);
        testProduct = product();
    }

    @Test
    public void validateTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product should not be null.");
        victim.validate(null);
        verify(validations).forEach(productValidation -> productValidation.validate(captor.capture()));
        assertNull(captor.getValue());
    }

    @Test
    public void shouldPassValidation() {
        victim.validate(testProduct);
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