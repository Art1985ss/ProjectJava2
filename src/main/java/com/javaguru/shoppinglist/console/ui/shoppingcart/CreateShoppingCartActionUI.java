package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ShoppingCartService;

import javax.swing.*;
import java.awt.*;

public class CreateShoppingCartActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Create shopping cart";
    private ShoppingCartService shoppingCartService;
    private JTextField nameField;

    public CreateShoppingCartActionUI(ShoppingCartService shoppingCartService) {
        super(ACTION_NAME);
        this.shoppingCartService = shoppingCartService;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0,2,5,5));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel label = new JLabel("Enter name for shopping cart");
        label.setVisible(true);
        container.add(label);
        nameField = new JTextField();
        nameField.setVisible(true);
        container.add(nameField);
        JButton button = new JButton("Create");
        button.addActionListener(actionEvent -> createShoppingCart());
        button.setVisible(true);
        container.add(button);
        button = new JButton("Cancel");
        button.addActionListener(actionEvent -> this.setVisible(false));
        button.setVisible(true);
        container.add(button);
        this.pack();
        this.validate();
        this.repaint();
    }


    @Override
    public void execute() {
        this.pack();
        this.validate();
        this.setVisible(true);
    }

    private void createShoppingCart(){
        try {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setName(this.nameField.getText());
            JOptionPane.showMessageDialog(null,
                    "Shopping cart created with id : " + shoppingCartService.createShoppingCart(shoppingCart),
                    "Shopping cart created",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
