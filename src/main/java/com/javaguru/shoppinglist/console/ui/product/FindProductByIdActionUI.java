package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class FindProductByIdActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Find product by id";
    private ProductService productService;
    private JPanel panel;
    private JTextField textFieldID;

    public FindProductByIdActionUI(ProductService productService) {
        super(ACTION_NAME);
        this.productService = productService;
        this.getContentPane().setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        JLabel label = new JLabel("Enter product id you want to find");
        label.setVisible(true);
        panel.add(label);
        textFieldID = new JTextField();
        textFieldID.setVisible(true);
        panel.add(textFieldID);
        JButton button = new JButton("Search");
        button.addActionListener(actionEvent -> findById());
        button.setVisible(true);
        panel.add(button);
        button = new JButton("Cancel");
        button.addActionListener(actionEvent -> this.setVisible(false));
        button.setVisible(true);
        panel.add(button);
        panel.setVisible(true);
        this.getContentPane().add(panel);
        this.pack();
    }

    @Override
    public void execute() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void findById() {
        try {
            JOptionPane.showMessageDialog(null,
                    productService.findById(Long.valueOf(textFieldID.getText())),
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
