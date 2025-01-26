package com.hitgram;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {
    public static void main(String[] args) throws Exception {
        // Set up the server
        Server server = new Server(8080); // Port 8080 for the server

        // Set up the servlet handler
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // Register the NGramServlet to handle requests
        handler.addServletWithMapping(NGramServlet.class, "/predict");

        // Start the server
        server.start();
        server.join();
    }
}
