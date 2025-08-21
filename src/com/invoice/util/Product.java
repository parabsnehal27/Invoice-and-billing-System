package com.invoice.util;

public class Product {
    public String name;
    public int quantity;
    public double unitPrice;
    public double gstRate;
    
    public Product(String name, int quantity, double unitPrice, double gstRate) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.gstRate=gstRate;
    }
    
    public double getTotalBeforeTax() {
    	return quantity*unitPrice;
    	
    }
    
    public double getGSTAmount() {
    	return getTotalBeforeTax() * gstRate/100.0;
    }

    public double getTotalWithTax() {
        return getTotalBeforeTax() + getGSTAmount();
    }

    public double getTotal() {
        return quantity * unitPrice;
    }
}
