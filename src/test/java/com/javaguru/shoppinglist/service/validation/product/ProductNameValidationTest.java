package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.product.RamProductRepository;
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

import static org.assertj.core.api.Assertions.*;
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
    private RamProductRepository productRepository;
    @InjectMocks
    private ProductNameValidation victim;
    private String exceptionMessage;
    private Product testProduct;
    @Captor
    ArgumentCaptor<String> captor;

    @Before
    public void setUp() {
        victim = new ProductNameValidation(productRepository);
        captor = ArgumentCaptor.forClass(String.class);
        testProduct = product();
    }

    @Test
    public void checkNullTest() {
        exceptionMessage = "Product should not be null.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        victim.checkNull(null);
        assertThatThrownBy(() -> victim.validate(null)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void validateTest() {
        exceptionMessage = "Product should not be null.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        victim.validate(null);
        assertThatThrownBy(() -> victim.validate(null)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void nameValidationTest() {
        exceptionMessage = "Product name should not be null.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        victim.nameValidation(null);
        assertThatThrownBy(() -> victim.nameValidation(null)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldGetNameExists() {
        exceptionMessage = "Product with such name already exists in the database.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        when(productRepository.findByName("Apple")).thenReturn(Optional.of(testProduct));
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldReturnNameTooShort() {
        exceptionMessage = "Product name length can't be shorter than " + MIN_NAME_LENGTH + " symbols.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setName("Ap");
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldReturnNameTooLong() {
        exceptionMessage = "Product name length can't be longer than " + MAX_NAME_LENGTH + " symbols.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        testProduct.setName("TooLongNameForThisField and should return exception");
        victim.validate(testProduct);
        assertThatThrownBy(() -> victim.validate(testProduct)).
                isInstanceOf(ProductValidationException.class).
                hasMessage(exceptionMessage);
    }

    @Test
    public void shouldPassValidation() {
        when(productRepository.findByName(testProduct.getName())).thenReturn(Optional.empty());
        victim.validate(testProduct);
        verify(productRepository).findByName(captor.capture());
        assertEquals(testProduct.getName(), captor.getValue());
        assertThat(captor.getValue()).isEqualTo(testProduct.getName());
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