package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class CreateProductActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Create product";


    private ProductService productService;
    private JPanel panel;
    private JTextField nameInputField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField discountField;
    private JTextField descriptionField;

    public CreateProductActionUI(ProductService productService) {
        super(ACTION_NAME);
        this.productService = productService;
        this.getContentPane().setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        nameInputField = new JTextField();
        addFieldToPanel(panel, nameInputField, "Enter product name ");
        categoryField = new JTextField();
        addFieldToPanel(panel, categoryField, "Enter product category");
        priceField = new JTextField();
        addFieldToPanel(panel, priceField, "Enter price for product");
        discountField = new JTextField();
        addFieldToPanel(panel, discountField, "Enter discount for product");
        descriptionField = new JTextField();
        addFieldToPanel(panel, descriptionField, "Enter description for product");
        JButton button = new JButton("Add product");
        button.addActionListener(actionEvent -> {
            try {
                addProduct();
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(),
                        "Exception",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        button.setVisible(true);
        panel.add(button);
        button = new JButton("Return");
        button.addActionListener(actionEvent -> this.setVisible(false));
        button.setVisible(true);
        panel.add(button);
        panel.revalidate();
        panel.setVisible(true);
        this.getContentPane().add(panel);
    }

    @Override
    public void execute() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addFieldToPanel(JPanel panel, JTextField textField, String text) {
        JLabel label = new JLabel(text);
        label.setVisible(true);
        textField.setMinimumSize(new Dimension(100, 0));
        textField.setVisible(true);

        panel.add(label);
        panel.add(textField);
    }

    private void addProduct() {
        Product product = new Product();
        product.setName(nameInputField.getText());
        product.setCategory(categoryField.getText());
        try {
            product.setPrice(new BigDecimal(priceField.getText()));
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Please enter valid numeric value in price field");
        }
        try {
            product.setDiscount(new BigDecimal(discountField.getText()));
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Please enter valid numeric value in discount field");
        }
        product.setDescription(descriptionField.getText());
        productService.createProduct(product);
        JOptionPane.showMessageDialog(null,
                "New product id : \n" + productService.createProduct(product),
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
