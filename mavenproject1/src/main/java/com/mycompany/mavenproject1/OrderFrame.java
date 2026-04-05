/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author admin
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderFrame extends JFrame {
    
    private List<OrderRecord> orderHistory = new ArrayList<>();
    private OrderTableModel tableModel = new OrderTableModel();
    
    private JCheckBox fireSauceCheck;
    private JCheckBox doubleMeatCheck;
    private JCheckBox snowBerriesCheck;
    private JCheckBox nordStewCheck;
    private JLabel priceLabel;
    private JTable historyTable;
    
    public OrderFrame() {
        setTitle("Нордское рагу - Система заказов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
        updatePriceDisplay();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel orderPanel = new JPanel(new BorderLayout(10, 10));
        orderPanel.setBorder(BorderFactory.createTitledBorder("Оформление заказа"));
        
        JPanel addonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        addonsPanel.setBorder(BorderFactory.createTitledBorder("Добавки (не более 3)"));
        
        fireSauceCheck = new JCheckBox("Огненный соус (+10 септимов)");
        doubleMeatCheck = new JCheckBox("Двойная порция оленины (+20 септимов)");
        snowBerriesCheck = new JCheckBox("Снежные ягоды (+6 септимов)");
        nordStewCheck = new JCheckBox("Нордская лепешка (+7 септимов)");
        
        
        addonsPanel.add(fireSauceCheck);
        addonsPanel.add(doubleMeatCheck);
        addonsPanel.add(snowBerriesCheck);
        addonsPanel.add(nordStewCheck);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        priceLabel = new JLabel("Итого: 50 септимов");
        pricePanel.add(priceLabel);
        
        JButton orderButton = new JButton("Оформить заказ");
        orderButton.addActionListener(e -> createOrder());
        
        bottomPanel.add(pricePanel, BorderLayout.CENTER);
        bottomPanel.add(orderButton, BorderLayout.SOUTH);
        
        orderPanel.add(addonsPanel, BorderLayout.CENTER);
        orderPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("История заказов"));
        
        historyTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(orderPanel, BorderLayout.NORTH);
        mainPanel.add(historyPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        fireSauceCheck.addActionListener(e -> updatePriceDisplay());
        doubleMeatCheck.addActionListener(e -> updatePriceDisplay());
        snowBerriesCheck.addActionListener(e -> updatePriceDisplay());
        nordStewCheck.addActionListener(e -> updatePriceDisplay());
        
        JCheckBox[] checkBoxes = {fireSauceCheck, doubleMeatCheck, snowBerriesCheck, nordStewCheck};

        for (JCheckBox checkBox : checkBoxes) {
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected() && getSelectedCount() > 3) {
                    checkBox.setSelected(false);
                    JOptionPane.showMessageDialog(this, 
                        "Нельзя выбрать более 3 добавок!", 
                        "Предупреждение", 
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    updatePriceDisplay();
                }
            });
        }
    }
      
    private int getSelectedCount() {
        int count = 0;
        if (fireSauceCheck.isSelected()) count++;
        if (doubleMeatCheck.isSelected()) count++;
        if (snowBerriesCheck.isSelected()) count++;
        if (nordStewCheck.isSelected()) count++;
        return count;
    }
    
    private void updatePriceDisplay() {
        Dish order = buildOrderItem();
        priceLabel.setText("Итого: " + order.getPrice() + " септимов");
    }
    
    private Dish buildOrderItem() {
        Dish order = new Stew();
        
        if (fireSauceCheck.isSelected()) {
            order = new FireSauceDecorator(order);
        }
        if (doubleMeatCheck.isSelected()) {
            order = new DoubleMeatDecorator(order);
        }
        if (snowBerriesCheck.isSelected()) {
            order = new SnowBerriesDecorator(order);
        }
        if (nordStewCheck.isSelected()) {
            order = new NordStewDecorator(order);
        }
        
        return order;
    }
    
    private void createOrder() {
        Dish order = buildOrderItem();
        
        int price = order.getPrice();
        String description = order.getDescription();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        
        orderHistory.add(new OrderRecord(time, description, price));
        tableModel.fireTableDataChanged();
        
        JOptionPane.showMessageDialog(this, 
            "Заказ оформлен!\n" + description + "\nСумма: " + price + " септимов", "Заказ принят",
            JOptionPane.INFORMATION_MESSAGE);
        
        fireSauceCheck.setSelected(false);
        doubleMeatCheck.setSelected(false);
        snowBerriesCheck.setSelected(false);
        nordStewCheck.setSelected(false);
        
        updatePriceDisplay();
    }
    
    private static class OrderRecord {
        String time;
        String name;
        int price;
        
        OrderRecord(String time, String name, int price) {
            this.time = time;
            this.name = name;
            this.price = price;
        }
    }
    
    private class OrderTableModel extends AbstractTableModel {
        private final String[] columns = {"Время заказа", "Полное имя заказа", "Цена (септимы)"};
        
        @Override
        public int getRowCount() {
            return orderHistory.size();
        }
        
        @Override
        public int getColumnCount() {
            return columns.length;
        }
        
        @Override
        public String getColumnName(int column) {
            return columns[column];
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            OrderRecord record = orderHistory.get(rowIndex);
            switch (columnIndex) {
                case 0: return record.time;
                case 1: return record.name;
                case 2: return record.price;
                default: return null;
            }
        }
    }
}