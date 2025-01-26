package com.hitgram;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/chatbot")
public class ChatbotServlet extends HttpServlet {
    private NGramModel nGramModel = new NGramModel();
    private static final int DEFAULT_N = 2; // Default N-Gram size (bigram)

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sentence = request.getParameter("inputSentence");
        String numWordsStr = request.getParameter("inputNumWords");
        String action = request.getParameter("action");

        if (sentence == null || sentence.isEmpty()) {
            response.getWriter().println("Please provide a sentence.");
            return;
        }

        int n = DEFAULT_N;
        try {
            n = Integer.parseInt(request.getParameter("nSlider"));
        } catch (NumberFormatException e) {
            n = DEFAULT_N;
        }

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if ("predict".equals(action)) {
            try {
                int numWordsToPredict = Integer.parseInt(numWordsStr);
                String result = nGramModel.predictNextWord(sentence, n, numWordsToPredict);
                out.println("<h3>Predicted Next Words: " + result + "</h3>");
            } catch (NumberFormatException ex) {
                out.println("Please enter a valid number of words to predict.");
            }
        } else if ("perplexity".equals(action)) {
            double perplexity = nGramModel.calculatePerplexity(sentence, n);
            out.println("<h3>Perplexity: " + perplexity + "</h3>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
