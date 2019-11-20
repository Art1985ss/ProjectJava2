package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.console.ui.product.ProductTableModel;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddProductsToShoppingCartActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Manage products in shopping cart";
    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private ShoppingCart shoppingCart;
    private Container container;

    public AddProductsToShoppingCartActionUI(ShoppingCartService shoppingCartService,
                                             ProductService productService) {
        super(ACTION_NAME);
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        container = this.getContentPane();
        container.setLayout(new GridLayout(0, 1, 5, 5));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);


        this.validate();
        this.repaint();

    }

    @Override
    public void execute() {
        chooseShoppingCart();
    }

    private void chooseShoppingCart() {
        container.removeAll();
        JLabel label = new JLabel("Enter shopping cart id you wish to manage");
        label.setVisible(true);
        container.add(label);
        JTextField idField = new JTextField();
        idField.setVisible(true);
        container.add(idField);
        JButton button = new JButton("Manage");
        button.addActionListener(actionEvent -> {
            try {
                shoppingCart = shoppingCartService.findById(Long.valueOf(idField.getText()));
                this.fillTables();
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(),
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        button.setVisible(true);
        container.add(button);
        button = new JButton("Cancel");
        button.addActionListener(actionEvent -> this.setVisible(false));
        button.setVisible(true);
        container.add(button);
        this.revalidate();
        this.pack();
        this.repaint();
        this.setVisible(true);


    }

    private void fillTables() {
        container.removeAll();
        JSplitPane splitPane = new JSplitPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Products in shopping cart, click to remove ");
        label.setVisible(true);
        panel.add(label);
        ProductTableModel inCartProductTableModel = new ProductTableModel(shoppingCart.getProductList());
        JTable tableCart = new JTable(inCartProductTableModel);
        tableCart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable jTable = (JTable) e.getSource();
                ProductTableModel model = (ProductTableModel) jTable.getModel();
                int row = jTable.getSelectedRow();
                Product product = getProduct(model, row);
                String[] options = {"Yes", "No"};
                int result = JOptionPane.showOptionDialog(
                        null,
                        "Delete product from shopping cart : \n" + product,
                        "Product delete",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (result == 0) {
                    shoppingCart.removeProduct(product);
                    tableCart.revalidate();
                    tableCart.repaint();
                }
            }
        });
        tableCart.setVisible(true);
        panel.add(new JScrollPane(tableCart));
        panel.add(buttonPanel());
        panel.revalidate();
        panel.setVisible(true);
        splitPane.add(panel, JSplitPane.LEFT);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        label = new JLabel("Available products to add, click to add");
        label.setVisible(true);
        panel.add(label);
        ProductTableModel productTableModel = new ProductTableModel(productService.getAllProducts());
        JTable productsTable = new JTable(productTableModel);
        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable jTable = (JTable) e.getSource();
                ProductTableModel model = (ProductTableModel) jTable.getModel();
                int row = jTable.getSelectedRow();
                Product product = getProduct(model, row);
                String[] options = {"Yes", "No"};
                int result = JOptionPane.showOptionDialog(null,
                        "Add product : \n" + product,
                        "Add product",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (result == 0) {
                    shoppingCart.addProduct(product);
                    tableCart.revalidate();
                    tableCart.repaint();
                }
            }
        });
        productsTable.setVisible(true);
        panel.add(new JScrollPane(productsTable));
        panel.setVisible(true);
        panel.revalidate();
        splitPane.add(panel, JSplitPane.RIGHT);
        splitPane.revalidate();
        container.add(splitPane);
        container.revalidate();
        this.pack();
        this.repaint();
        this.setVisible(true);
    }

    private Product getProduct(ProductTableModel model, int row) {
        return model.getRow(row);
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2,5,5));
        JButton button = new JButton("Apply");
        button.addActionListener(actionEvent -> {
            shoppingCartService.saveChanges(shoppingCart);
            shoppingCart = null;
            this.setVisible(false);
        });
        button.setVisible(true);
        panel.add(button);
        button = new JButton("Cancel");
        button.addActionListener(actionEvent -> {
            shoppingCart = null;
            this.setVisible(false);
        });
        button.setVisible(true);
        panel.add(button);
        panel.setVisible(true);
        return panel;

    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
