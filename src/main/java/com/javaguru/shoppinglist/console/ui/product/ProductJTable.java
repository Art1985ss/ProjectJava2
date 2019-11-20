package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.ProductService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.List;

public class ProductJTable extends JTable {
    private ProductService productService;
    private Product product;

    public ProductJTable(ProductService productService) {
        this.productService = productService;
        ProductTableModel model = new ProductTableModel(productService.getAllProducts());
        this.setModel(model);
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                ProductTableModel model1 = (ProductTableModel) table.getModel();
                product = model1.getRow(row);
                JOptionPane.showMessageDialog(table,
                        String.format("Product total price : %.2f", product.getTotalPrice()),
                        "Product total price",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        this.addMouseListener(mouseListener);
        this.setVisible(true);
    }
}
