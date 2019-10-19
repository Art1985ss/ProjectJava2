package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductNameValidationTest {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 32;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductNameValidation victim;
    @Captor
    ArgumentCaptor<String> captor;

    @Before
    public void setUp() {
        victim = new ProductNameValidation(productRepository);
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    public void checkNullTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product should not be null.");
        victim.checkNull(null);
    }

    @Test
    public void validateTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product should not be null.");
        victim.validate(null);
    }

    @Test
    public void nameValidationTest() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product name should not be null.");
        victim.nameValidation(null);
    }

    @Test
    public void shouldGetNameExists() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product with such name already exists in the database.");
        when(productRepository.findByName("Apple")).thenReturn(Optional.of(product()));
        victim.validate(product());
    }

    @Test
    public void shouldReturnNameTooShort() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product name length can't be shorter than " + MIN_NAME_LENGTH + " symbols.");
        Product product = product();
        product.setName("Ap");
        victim.validate(product);
    }

    @Test
    public void shouldReturnNameTooLong() {
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage("Product name length can't be longer than " + MAX_NAME_LENGTH + " symbols.");
        Product product = product();
        product.setName("TooLongNameForThisField and should return exception");
        victim.validate(product);
    }

    @Test
    public void shouldPassValidation() {
        Product product = product();
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        victim.validate(product);
        verify(productRepository).findByName(captor.capture());
        assertEquals(product.getName(), captor.getValue());
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