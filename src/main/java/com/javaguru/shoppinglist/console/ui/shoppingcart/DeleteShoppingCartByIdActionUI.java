package com.javaguru.shoppinglist.console.ui.shoppingcart;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.service.ShoppingCartService;

import javax.swing.*;
import java.awt.*;

public class DeleteShoppingCartByIdActionUI extends JFrame implements ActionUI {
    private static final String ACTION_NAME = "Delete shopping cart by id";
    private ShoppingCartService shoppingCartService;
    private JTextField idField;

    public DeleteShoppingCartByIdActionUI(ShoppingCartService shoppingCartService) {
        super(ACTION_NAME);
        this.shoppingCartService = shoppingCartService;
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0, 2, 5, 5));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel label = new JLabel("Enter shopping cart id you wish to delete");
        label.setVisible(true);
        container.add(label);
        idField = new JTextField();
        idField.setVisible(true);
        container.add(idField);
        JButton button = new JButton("Delete");
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
                    "Shopping cart you have deleted : \n" +
                            shoppingCartService.delete(Long.valueOf(idField.getText())),
                    "Shopping cart deleted",
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
