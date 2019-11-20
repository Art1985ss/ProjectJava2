package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ShoppingCartService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class FindShoppingCartByIdActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Find shopping cart by id";
    private ShoppingCartService shoppingCartService;
    private JTextField idField;

    public FindShoppingCartByIdActionUI(ShoppingCartService shoppingCartService) {
        super(ACTION_NAME);
        this.shoppingCartService = shoppingCartService;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0, 2, 5, 5));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel label = new JLabel("Enter shopping cart id you wish to find");
        label.setVisible(true);
        container.add(label);
        idField = new JTextField();
        idField.setVisible(true);
        container.add(idField);
        JButton button = new JButton("Search");
        button.addActionListener(actionEvent -> findById());
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

    private void findById() {
        try {
            ShoppingCart shoppingCart = shoppingCartService.findById(Long.valueOf(idField.getText()));
            JOptionPane.showMessageDialog(null,
                    shoppingCart,
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
