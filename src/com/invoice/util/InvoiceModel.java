package com.invoice.util;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Font;

public class InvoiceModel {
	public String client;
	public String clientemail;
	public String clientphone;
	public String companyname;
    public List<Product> products = new ArrayList<>();
	

    public void addProduct(Product p) {
        products.add(p);
    }

    public double getTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.getTotal();
        }
        return total;
    }
    public double getSubtotal() {
        return products.stream().mapToDouble(Product::getTotalBeforeTax).sum();
    }

    public double getTotalGST() {
        return products.stream().mapToDouble(Product::getGSTAmount).sum();
    }

    public double getGrandTotal() {
        return products.stream().mapToDouble(Product::getTotalWithTax).sum();
    }
}
