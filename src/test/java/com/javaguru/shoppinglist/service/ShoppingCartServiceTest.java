package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.repository.shoppingcart.RamShoppingCartRepository;
import com.javaguru.shoppinglist.service.validation.shoppingcart.ShoppingCartNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private RamShoppingCartRepository shoppingCartRepository;
    private ShoppingCart mockShoppingCart;
    @InjectMocks
    private ShoppingCartService victim;
    private ShoppingCart testShoppingCart;

    @Before
    public void setUp() {
        mockShoppingCart = Mockito.mock(ShoppingCart.class);
        victim = new ShoppingCartService(shoppingCartRepository);
        testShoppingCart = shoppingCart();
    }

    @Test
    public void createShoppingCartShouldThrowException() {
        expectedException.expect(ShoppingCartNotFoundException.class);
        expectedException.expectMessage("Shopping cart was not found in database.");
        when(shoppingCartRepository.save(testShoppingCart)).thenReturn(Optional.empty());
        victim.createShoppingCart(shoppingCart());
    }

    @Test
    public void createShoppingCartShouldReturnCartId() {
        when(shoppingCartRepository.save(testShoppingCart)).thenReturn(Optional.ofNullable(testShoppingCart));
        Long id = victim.createShoppingCart(testShoppingCart);
        assertEquals(testShoppingCart.getId(), id);
    }

    @Test
    public void findByIdShouldThrowException() {
        Long id = 2L;
        expectedException.expect(ShoppingCartNotFoundException.class);
        expectedException.expectMessage("Shopping cart with id " + id + " was not found.");
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());
        victim.findById(id);
    }

    @Test
    public void findByIdShouldReturnShoppingCart() {
        Long id = testShoppingCart.getId();
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.ofNullable(testShoppingCart));
        ShoppingCart shoppingCart = victim.findById(id);
        assertEquals(testShoppingCart, shoppingCart);
    }

    @Test
    public void findByNameShouldThrowException() {
        String name = testShoppingCart.getName();
        expectedException.expect(ShoppingCartNotFoundException.class);
        expectedException.expectMessage("Shopping cart with name " + name + " was not found.");
        when(shoppingCartRepository.findByName(name)).thenReturn(Optional.empty());
        victim.findByName(name);
    }

    @Test
    public void findByNameShouldReturnCart() {
        String name = testShoppingCart.getName();
        when(shoppingCartRepository.findByName(name)).thenReturn(Optional.ofNullable(testShoppingCart));
        ShoppingCart shoppingCart = victim.findByName(name);
        assertEquals(testShoppingCart, shoppingCart);
    }

    @Test
    public void deleteShouldThrowException() {
        Long id = 2L;
        expectedException.expect(ShoppingCartNotFoundException.class);
        expectedException.expectMessage("Shopping cart didn't exist to delete.");
        when(shoppingCartRepository.delete(id)).thenReturn(Optional.empty());
        victim.delete(id);
    }

    @Test
    public void deleteShouldReturnCart() {
        Long id = testShoppingCart.getId();
        when(shoppingCartRepository.delete(id)).thenReturn(Optional.ofNullable(testShoppingCart));
        ShoppingCart shoppingCart = victim.delete(id);
        assertEquals(testShoppingCart, shoppingCart);
    }

    @Test
    public void addProductShouldThrowException() {
        expectedException.expect(ShoppingCartNotFoundException.class);
        expectedException.expectMessage("Shopping cart product list is empty.");
        Product product = product();
        when(mockShoppingCart.addProduct(product)).thenReturn(Optional.empty());
        victim.addProduct(mockShoppingCart, product);
    }

    @Test
    public void addProductShouldReturnList() {
        Product product = product();
        List<Product> productList = victim.addProduct(testShoppingCart, product);
        assertNotNull(productList);
    }

    @Test
    public void getTotalPriceOfProductsFromShoppingCartShouldThrowException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Could not calculate total price for products in this shopping cart.");
        when(mockShoppingCart.getPriceTotal()).thenReturn(Optional.empty());
        victim.getTotalPriceOfProductsFromShoppingCart(mockShoppingCart);
    }

    @Test
    public void getTotalPriceOfProductsFromShoppingCartShouldReturnTotalPrice() {
        when(mockShoppingCart.getPriceTotal()).thenReturn(Optional.of(new BigDecimal("200")));
        BigDecimal totalPrice = victim.getTotalPriceOfProductsFromShoppingCart(mockShoppingCart);
        assertNotNull(totalPrice);
    }

    private ShoppingCart shoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setName("Cart1");
        return shoppingCart;
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