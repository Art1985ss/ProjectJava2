package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.entity.Product;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> productList;
    private String[] columnNames;
    private Class[] columnClasses;

    public ProductTableModel(List<Product> products) {
        productList = products;
        columnNames = new String[]{"ID", "Name", "Category", "Price", "Discount", "Description"};
        columnClasses = new Class[]
                {Long.class, String.class, String.class, BigDecimal.class, BigDecimal.class, String.class};

    }

    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Product product = productList.get(row);
        switch (col) {
            case 0:
                return product.getId();
            case 1:
                return product.getName();
            case 2:
                return product.getCategory();
            case 3:
                return product.getPrice();
            case 4:
                return product.getDiscount();
            case 5:
                return product.getDescription();
            default:
                return null;
        }
    }

    public Product getRow(int row) {
        return productList.get(row);
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class getColumnClass(int column) {
        return columnClasses[column];
    }
}
