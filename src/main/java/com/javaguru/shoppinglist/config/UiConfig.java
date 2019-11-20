package com.javaguru.shoppinglist.config;

import com.javaguru.shoppinglist.console.ui.*;
import com.javaguru.shoppinglist.console.ui.product.*;
import com.javaguru.shoppinglist.console.ui.shoppingcart.*;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.javaguru.shoppinglist")
@Profile({"ui"})
public class UiConfig {
    private ActionUI createProductActionUI;
    private ActionUI findProductByIdActionUI;
    private ActionUI findProductByNameActionUI;
    private ActionUI displayAllProductsActionUI;
    private ActionUI createShoppingCartActionUI;
    private ActionUI findShoppingCartByIdActionUI;
    private ActionUI findShoppingCartByNameActionUI;
    private ActionUI deleteShoppingCartByIdActionUI;
    private ActionUI addProductsToShoppingCartActionUI;

    @Autowired
    public void createActions(ProductService productService,
                              ShoppingCartService shoppingCartService) {
        createProductActionUI = new CreateProductActionUI(productService);
        findProductByIdActionUI = new FindProductByIdActionUI(productService);
        findProductByNameActionUI = new FindProductByNameActionUI(productService);
        displayAllProductsActionUI = new DisplayAllProductsActionUI(productService);

        createShoppingCartActionUI = new CreateShoppingCartActionUI(shoppingCartService);
        findShoppingCartByIdActionUI = new FindShoppingCartByIdActionUI(shoppingCartService);
        findShoppingCartByNameActionUI = new FindShoppingCartByNameActionUI(shoppingCartService);
        deleteShoppingCartByIdActionUI = new DeleteShoppingCartByIdActionUI(shoppingCartService);
        addProductsToShoppingCartActionUI = new AddProductsToShoppingCartActionUI(shoppingCartService,
                productService);
    }


    @Bean
    public List<ActionUI> productActions() {
        List<ActionUI> actions = new ArrayList<>();
        actions.add(createProductActionUI);
        actions.add(findProductByIdActionUI);
        actions.add(findProductByNameActionUI);
        actions.add(displayAllProductsActionUI);
        return actions;
    }

    @Bean
    public List<ActionUI> shoppingCartActions() {
        List<ActionUI> actions = new ArrayList<>();
        actions.add(createShoppingCartActionUI);
        actions.add(findShoppingCartByIdActionUI);
        actions.add(findShoppingCartByNameActionUI);
        actions.add(deleteShoppingCartByIdActionUI);
        actions.add(addProductsToShoppingCartActionUI);
        return actions;
    }

    @Bean
    public ProductMenuUI productMenuUI() {
        return new ProductMenuUI(productActions());
    }

    @Bean
    public ShoppingCartMenuUI shoppingCartMenuUI() {
        return new ShoppingCartMenuUI(shoppingCartActions());
    }

    @Bean
    public MainMenuUI mainMenuUI() {
        List<ActionUI> menuList = new ArrayList<>();
        menuList.add(productMenuUI());
        menuList.add(shoppingCartMenuUI());
        return new MainMenuUI(menuList);
    }
}
