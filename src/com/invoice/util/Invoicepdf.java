package com.invoice.util;

import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class Invoicepdf {

    public static void createInvoice(String companyName, String clientName) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
            document.open();

            // Company Name
            Font companyFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(0, 102, 204));
            Paragraph companyPara = new Paragraph(companyName, companyFont);
            companyPara.setAlignment(Element.ALIGN_CENTER);
            document.add(companyPara);

            // Small separator
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(BaseColor.LIGHT_GRAY);
            document.add(new Chunk(ls));

            // Client Info
            Font clientFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Client: " + clientName, clientFont));
            document.add(Chunk.NEWLINE);

            // Product Table
            PdfPTable table = new PdfPTable(6); // 6 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Header
            Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor headerBg = new BaseColor(0, 102, 204);

            String[] headers = {"Product", "Qty", "Unit Price", "GST %", "GST Amt", "Total"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
                cell.setBackgroundColor(headerBg);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Example Product Row
            table.addCell("Laptop");
            table.addCell("1");
            table.addCell("10000.00");
            table.addCell("18");
            table.addCell("1800.00");
            table.addCell("11800.00");

            document.add(table);

            // Totals
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph gstPara = new Paragraph("Total GST: 1800.00", totalFont);
            gstPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(gstPara);

            Paragraph grandTotalPara = new Paragraph("Grand Total: 11800.00", totalFont);
            grandTotalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(grandTotalPara);

            document.close();
            System.out.println("Invoice created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
