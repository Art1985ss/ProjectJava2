package com.javaguru.shoppinglist.console.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainMenuUI extends JFrame implements UserInterface, ActionUI {
    private static final String FORM_NAME = "Shopping list application";
    private List<ActionUI> menuList;

    public MainMenuUI(List<ActionUI> menuList) {
        super(FORM_NAME);
        this.menuList = menuList;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0, 1, 5, 5));

    }

    @Override
    public void createMenu() {
        menuList.forEach(menu -> {
            JButton button = new JButton(menu.toString());
            button.addActionListener(actionEvent -> menu.execute());
            button.setVisible(true);
            this.getContentPane().add(button);
        });
        JButton button = new JButton("Exit");
        button.addActionListener(actionEvent -> System.exit(0));
        button.setVisible(true);
        this.getContentPane().add(button);
        this.pack();
        this.setVisible(true);

    }


    @Override
    public void execute() {
        createMenu();
    }

    @Override
    public String toString() {
        return FORM_NAME;
    }
}
