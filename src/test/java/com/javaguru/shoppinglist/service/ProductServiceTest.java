package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.RamProductRepository;
import com.javaguru.shoppinglist.service.validation.product.ProductNotFoundException;
import com.javaguru.shoppinglist.service.validation.product.ProductValidationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private RamProductRepository productRepository;
    @Mock
    private ProductValidationService productValidationService;
    @InjectMocks
    private ProductService victim;
    private Product testProduct;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

    @Before
    public void setUp() {
        victim = new ProductService(productRepository, productValidationService);
        testProduct = product();
    }

    @Test
    public void createProductShouldThrowException() {
        expectedException.expect(ProductNotFoundException.class);
        expectedException.expectMessage("Product was not found.");
        when(productRepository.add(testProduct)).thenReturn(Optional.empty());
        victim.createProduct(product());
    }

    @Test
    public void createProductShouldReturnProductId() {
        when(productRepository.add(testProduct)).thenReturn(Optional.ofNullable(testProduct));
        Long id = victim.createProduct(testProduct);
        verify(productValidationService).validate(productArgumentCaptor.capture());
        assertEquals(testProduct.getId(), id);
        assertEquals(testProduct, productArgumentCaptor.getValue());
    }

    @Test
    public void findByIdShouldThrowException() {
        Long id = 2L;
        expectedException.expect(ProductNotFoundException.class);
        expectedException.expectMessage("Product with id " + id + " was not found.");
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        victim.findById(id);
    }

    @Test
    public void findByIdShouldReturnProduct() {
        Long id = testProduct.getId();
        when(productRepository.findById(id)).thenReturn(Optional.ofNullable(testProduct));
        Product product = victim.findById(id);
        assertEquals(testProduct, product);
    }

    @Test
    public void findByNameShouldThrowException() {
        expectedException.expect(ProductNotFoundException.class);
        expectedException.expectMessage("Product with name " + testProduct.getName() + " not found.");
        when(productRepository.findByName(product().getName())).thenReturn(Optional.empty());
        victim.findByName(testProduct.getName());
    }

    @Test
    public void findByNameShouldReturnProduct() {
        String name = testProduct.getName();
        when(productRepository.findByName(name)).thenReturn(Optional.ofNullable(testProduct));
        Product product = victim.findByName(name);
        assertEquals(testProduct, product);
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