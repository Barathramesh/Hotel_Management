package com.project.Hotel.Management.Service;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public void generateInvoice(String customerName, int roomNumber, double amount, String bookingNumber) {
        try {
            // Ensure the documents folder exists
            File folder = new File("documents");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // File path inside documents folder
            String fileName = "documents/invoice_" + bookingNumber + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("🏨 Hotel Booking Invoice"));
            document.add(new Paragraph("Customer: " + customerName));
            document.add(new Paragraph("Room Number: " + roomNumber));
            document.add(new Paragraph("Booking Number: " + bookingNumber));
            document.add(new Paragraph("Amount: ₹" + amount));
            document.add(new Paragraph("Thank you for booking with us!"));
            document.close();

            System.out.println("✅ PDF saved at: " + fileName);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
