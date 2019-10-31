package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.config.ApplicationConfig;
import com.javaguru.shoppinglist.config.MySqlConfig;
import com.javaguru.shoppinglist.console.MenuConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ShoppingListApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        applicationContext.register(MySqlConfig.class);
        MenuConsole menuConsole = applicationContext.getBean(MenuConsole.class);
        menuConsole.execute();
    }
}
