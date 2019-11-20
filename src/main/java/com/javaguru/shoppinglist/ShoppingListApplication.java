package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.config.ApplicationConfig;
import com.javaguru.shoppinglist.config.HibernateAppConfig;
import com.javaguru.shoppinglist.config.MySqlAppConfig;
import com.javaguru.shoppinglist.console.ui.MainMenuUI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ShoppingListApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        applicationContext.register(MySqlAppConfig.class);
        applicationContext.register(HibernateAppConfig.class);
        //MenuConsole menuConsole = applicationContext.getBean(MenuConsole.class);
        //menuConsole.execute();

        MainMenuUI mainMenuUI = applicationContext.getBean(MainMenuUI.class);
        mainMenuUI.execute();
    }
}
