package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class DisplayAllProductsActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Show all products";
    private ProductService productService;

    public DisplayAllProductsActionUI(ProductService productService) {
        super(ACTION_NAME);
        this.productService = productService;
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void execute() {
        Container container = this.getContentPane();
        container.removeAll();
        ProductJTable productJTable = new ProductJTable(productService);
        container.add(new JScrollPane(productJTable));
        this.revalidate();
        this.pack();
        this.repaint();
        this.setVisible(true);
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
