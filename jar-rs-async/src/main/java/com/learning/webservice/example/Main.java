package com.learning.webservice.example;

import com.learning.webservice.example.config.JerseyConfig;
import com.learning.webservice.example.repository.BookDao;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer createServer() {

        final BookDao bookDao = new BookDao();

        // create a resource config that scans for JAX-RS resources and providers
        // in com.learning.webservice.example package
        final ResourceConfig rc = new JerseyConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, false);
    }

    /**
     * Main method.
     * @param args JVM arguments provided when starting the application.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        final HttpServer server = createServer();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
            }
        }));

        server.start();

        System.out.println(String.format("Application started.\nTry out %s\nStop the application using CTRL+C",
                BASE_URI));

        Thread.currentThread().join(0);
    }
}

