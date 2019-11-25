package com.javaguru.shoppinglist.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MenuController {

    @RequestMapping(method = RequestMethod.GET)
    public String showMainMenu(){
        return "index";
    }

    @RequestMapping("product_menu")
    public String showProductMenuForm(){
        return "product";
    }

    @RequestMapping("shopping_cart_menu")
    public String showCartMenuForm(){
        return "shopping_cart";
    }
}
