package com.javaguru.shoppinglist.console.ui.product;

import com.javaguru.shoppinglist.console.ui.ActionUI;
import com.javaguru.shoppinglist.console.ui.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductMenuUI extends JFrame implements UserInterface, ActionUI {
    private static final String ACTION_NAME = "Product menu";
    private List<ActionUI> actions;
    private JPanel panel;

    public ProductMenuUI(List<ActionUI> actions) {
        super(ACTION_NAME);
        this.actions = actions;
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));
        createMenu();
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

    @Override
    public void createMenu() {
        if (actions.isEmpty()) return;
        actions.forEach(action -> {
            JButton button = new JButton(action.toString());
            button.addActionListener(actionEvent -> action.execute());
            button.setVisible(true);
            panel.add(button);
        });
        JButton button = new JButton("Close");
        button.addActionListener(actionEvent -> this.setVisible(false));
        panel.add(button);
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }
}
