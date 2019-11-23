package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.console.ui.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShoppingCartMenuUI extends JFrame implements UserInterface, ActionUI {
    private static final String ACTION_NAME = "Shopping Cart management";
    private List<ActionUI> actions;


    public ShoppingCartMenuUI(List<ActionUI> actions) {
        super(ACTION_NAME);
        this.actions = actions;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0,1,5,5));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        createMenu();
        this.pack();
        this.validate();
    }

    @Override
    public void execute() {
        this.setLocationRelativeTo(null);
        this.pack();
        this.validate();
        this.setVisible(true);
    }

    @Override
    public void createMenu() {
        this.actions.forEach(actionUI -> {
            JButton button = new JButton(actionUI.toString());
            button.addActionListener(actionEvent -> actionUI.execute());
            button.setVisible(true);
            this.getContentPane().add(button);
        });
        JButton button = new JButton("Close");
        button.addActionListener(actionEvent -> this.setVisible(false));
        button.setVisible(true);
        this.getContentPane().add(button);
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
