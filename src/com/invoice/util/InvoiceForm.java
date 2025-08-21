package com.invoice.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceForm extends JFrame {

    JTextField companyField, clientField, txtClientEmail, productField, qtyField, priceField, gstField;
    JButton addBtn, generateBtn;
    JTable productTable;
    DefaultTableModel tableModel;

    List<Product> productList = new ArrayList<>();

    public InvoiceForm() {
        setTitle("Invoice Generator");
        setSize(800, 600);
        setLayout(null);

        // --- New Company Name field ---
        JLabel companyLabel = new JLabel("Company Name:");
        companyLabel.setBounds(30, 20, 100, 20);
        add(companyLabel);

        companyField = new JTextField();
        companyField.setBounds(140, 20, 200, 20);
        add(companyField);

        JLabel l1 = new JLabel("Client Name:");
        l1.setBounds(30, 50, 100, 20);
        add(l1);

        clientField = new JTextField();
        clientField.setBounds(140, 50, 200, 20);
        add(clientField);

        JLabel emailLabel = new JLabel("Client Email:");
        emailLabel.setBounds(30, 75, 100, 20);
        add(emailLabel);

        txtClientEmail = new JTextField();
        txtClientEmail.setBounds(140, 75, 200, 20);
        add(txtClientEmail);

        JLabel l2 = new JLabel("Product:");
        l2.setBounds(30, 110, 100, 20);
        add(l2);

        productField = new JTextField();
        productField.setBounds(140, 110, 120, 20);
        add(productField);

        JLabel l3 = new JLabel("Qty:");
        l3.setBounds(270, 110, 40, 20);
        add(l3);

        qtyField = new JTextField();
        qtyField.setBounds(310, 110, 50, 20);
        add(qtyField);

        JLabel l4 = new JLabel("Price:");
        l4.setBounds(370, 110, 50, 20);
        add(l4);

        priceField = new JTextField();
        priceField.setBounds(420, 110, 60, 20);
        add(priceField);

        JLabel l5 = new JLabel("GST %:");
        l5.setBounds(490, 110, 50, 20);
        add(l5);

        gstField = new JTextField("18");
        gstField.setBounds(540, 110, 40, 20);
        add(gstField);

        addBtn = new JButton("Add Product");
        addBtn.setBounds(590, 110, 120, 20);
        add(addBtn);

        tableModel = new DefaultTableModel(new String[]{"Product", "Qty", "Price", "GST %", "Total with Tax"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(30, 150, 680, 300);
        add(scrollPane);

        generateBtn = new JButton("Generate Invoice");
        generateBtn.setBounds(300, 470, 180, 30);
        add(generateBtn);

        // Add product button logic
        addBtn.addActionListener(e -> {
            try {
                String name = productField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                double price = Double.parseDouble(priceField.getText());
                double gstRate = Double.parseDouble(gstField.getText());

                Product p = new Product(name, qty, price, gstRate);
                productList.add(p);

                tableModel.addRow(new Object[]{p.name, p.quantity, p.unitPrice, p.gstRate, p.getTotalWithTax()});

                // Clear fields
                productField.setText("");
                qtyField.setText("");
                priceField.setText("");
                gstField.setText("18");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid product details.");
            }
        });

        // Generate invoice button logic
        generateBtn.addActionListener(e -> {
            if (productList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Add at least one product.");
                return;
            }

            String companyName = companyField.getText();
            if (companyName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter company name.");
                return;
            }

            String client = clientField.getText();
            if (client.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter client name.");
                return;
            }

            String clientEmail = txtClientEmail.getText();
            if (clientEmail.isEmpty() || !clientEmail.contains("@")) {
                JOptionPane.showMessageDialog(this, "Enter valid email address.");
                return;
            }

            InvoiceModel invoice = new InvoiceModel();
            invoice.client = client;
            invoice.companyname = companyName; // <-- NEW

            for (Product p : productList) {
                invoice.addProduct(p);
            }

            try {
                Connection con = DBConnection.getConnection();
                for (Product p : productList) {
                    PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO invoices(client_name, product_name, quantity, unit_price, gst_rate, total) VALUES ( ?, ?, ?, ?, ?, ?)"
                    );
                   
                    ps.setString(1, client);
                    ps.setString(2, p.name);
                    ps.setInt(3, p.quantity);
                    ps.setDouble(4, p.unitPrice);
                    ps.setDouble(5, p.gstRate);
                    ps.setDouble(6, p.getTotalWithTax());
                    ps.executeUpdate();
                }
                con.close();

                String filePath = "invoices/" + client.replaceAll(" ", "_") + "_Invoice.pdf";
                InvoiceGenerator.generatepdf(invoice);

                String subject = "Invoice " + " from " + companyName;
                String body = "Hi " + client + ",\n\nPlease find attached your invoice from " 
                            + companyName + ".\n\nThanks,\n" + companyName;

                EmailSender.sendEmailWithAttachment(clientEmail, subject, body, filePath);

                JOptionPane.showMessageDialog(null, "Invoice generated and emailed!");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving invoice.");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
