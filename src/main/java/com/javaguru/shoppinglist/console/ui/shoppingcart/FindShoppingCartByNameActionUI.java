package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.service.ShoppingCartService;

import javax.swing.*;
import java.awt.*;

public class FindShoppingCartByNameActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Find shopping cart by name";
    private ShoppingCartService shoppingCartService;
    private JTextField nameField;

    public FindShoppingCartByNameActionUI(ShoppingCartService shoppingCartService) {
        super(ACTION_NAME);
        this.shoppingCartService = shoppingCartService;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0, 2, 5, 5));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel label = new JLabel("Enter shopping cart name you wish to find");
        label.setVisible(true);
        container.add(label);
        nameField = new JTextField();
        nameField.setVisible(true);
        container.add(nameField);
        JButton button = new JButton("Search");
        button.addActionListener(actionEvent -> findByName());
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

    private void findByName() {
        try {
            JOptionPane.showMessageDialog(null,
                    shoppingCartService.findByName(nameField.getText()),
                    "Shopping cart found",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void execute() {
        this.pack();
        this.validate();
        this.setVisible(true);
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
