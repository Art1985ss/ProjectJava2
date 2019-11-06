package com.javaguru.shoppinglist.service.validation.product;

import com.javaguru.shoppinglist.entity.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductValidationServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ProductNameValidation productNameValidation;
    @Mock
    private ProductPriceValidation productPriceValidation;
    @Mock
    private ProductDiscountValidation productDiscountValidation;
    @InjectMocks
    private ProductValidationService victim;
    private Product testProduct;
    @Captor
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    @Before
    public void setUp() {
        Set<ProductValidation> validations = new HashSet<>();
        validations.add(productNameValidation);
        validations.add(productPriceValidation);
        validations.add(productDiscountValidation);
        victim = new ProductValidationService(validations);
        testProduct = product();
    }

    @Test
    public void validateTest() {
        String exceptionMessage = "Product should not be null.";
        expectedException.expect(ProductValidationException.class);
        expectedException.expectMessage(exceptionMessage);
        doThrow(new ProductValidationException(exceptionMessage)).when(productNameValidation).validate(null);
        victim.validate(null);
    }

    @Test
    public void shouldPassValidation() {
        victim.validate(testProduct);
        verify(productNameValidation).validate(captor.capture());
        verify(productPriceValidation).validate(captor.capture());
        verify(productDiscountValidation).validate(captor.capture());
        assertThat(captor.getAllValues()).containsOnly(testProduct);
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