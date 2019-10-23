package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.config.ApplicationConfig;
import com.javaguru.shoppinglist.console.MenuConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ShoppingListApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MenuConsole menuConsole = applicationContext.getBean(MenuConsole.class);
        menuConsole.execute();
    }
}
