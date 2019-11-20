package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class FindProductByNameActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Find product by name";
    private ProductService productService;
    private JTextField nameField;

    public FindProductByNameActionUI(ProductService productService) {
        super(ACTION_NAME);
        this.productService = productService;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0, 2, 5, 5));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JLabel label = new JLabel("Enter name you wish to find");
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
    }

    @Override
    public void execute() {
        this.pack();
        this.validate();
        this.setVisible(true);
    }

    private void findByName() {
        try {
            JOptionPane.showMessageDialog(null,
                    productService.findByName(nameField.getText()),
                    "Product found",
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
