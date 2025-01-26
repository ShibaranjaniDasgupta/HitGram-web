package com.hitgram;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class NGramUtils {
    public String readTextFromFile(File file) throws IOException {
        if (file.getName().endsWith(".pdf")) {
            return extractTextFromPDF(file);
        } else {
            return readTextFromPlainFile(file);
        }
    }

    private String readTextFromPlainFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append(" ");
        }
        reader.close();
        return content.toString().trim();
    }

    private String extractTextFromPDF(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfText = pdfStripper.getText(document);
        document.close();
        return pdfText.trim();
    }
}
