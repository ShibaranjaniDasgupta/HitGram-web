package com.hitgram;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/upload")
public class NGramServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static NGramModel ngramModel = new NGramModel();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // File Upload Handling
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> formItems = upload.parseRequest(request);
            FileItem uploadedFile = formItems.get(0);  // Assuming only one file upload

            if (uploadedFile != null && uploadedFile.getSize() > 0) {
                String fileName = uploadedFile.getName();
                String filePath = "uploads/" + fileName;
                File storeFile = new File(filePath);
                uploadedFile.write(storeFile);
                // Build NGram model from uploaded file
                ngramModel.buildModel(filePath);
                response.getWriter().println("Model built successfully from uploaded file.");
            }
        } catch (Exception ex) {
            response.getWriter().println("Error while uploading file: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputText = request.getParameter("sentence");
        String numWords = request.getParameter("numWords");

        if (inputText != null && numWords != null) {
            List<String> prediction = ngramModel.predictText(inputText, Integer.parseInt(numWords));
            response.getWriter().println("Predicted Text: " + String.join(" ", prediction));
        }
    }
}
