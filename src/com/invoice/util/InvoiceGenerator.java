package com.invoice.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;

public class InvoiceGenerator {
	public static void generatepdf(InvoiceModel inv) {
        Document doc = new Document();

        try {
            String filepath = "invoices/" + inv.client.replaceAll(" ", "_") + "_Invoice.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(filepath));
            doc.open();

            doc.add(new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            doc.add(new Paragraph(inv.companyname + FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
            doc.add(new Paragraph("Client: " + inv.client));
            doc.add(new Paragraph(" "));
            

            PdfPTable table = new PdfPTable(6);
            table.addCell("Product");
            table.addCell("Qty");
            table.addCell("Unit Price");
            table.addCell("GST %");
            table.addCell("GST Amt");
            table.addCell("Total");

            for (Product p : inv.products) {
                table.addCell(p.name);
                table.addCell(String.valueOf(p.quantity));
                table.addCell(String.valueOf(p.unitPrice));
                table.addCell(String.valueOf(p.gstRate));
                table.addCell(String.format("%.2f", p.getGSTAmount()));
                table.addCell(String.format("%.2f", p.getTotalWithTax()));
            }


            doc.add(table);
            doc.add(new Paragraph(" "));
            //doc.add(new Paragraph("Subtotal: ₹" + String.format("%.2f", inv.getSubtotal())));
            doc.add(new Paragraph("Total GST: ₹" + String.format("%.2f", inv.getTotalGST())));
            doc.add(new Paragraph("Grand Total: ₹" + String.format("%.2f", inv.getGrandTotal())));


            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}