package com.mycompany.httpserver;
import java.net.*;
import java.io.*;

public class WebServer {
    private static final int port = 8080;
    private ServerSocket serverSocket;
    private boolean running;
    private FileHandler fileHandler;
    private ApiHandler apiHandler;
    private static String staticFilesPath; 

    public static void main(String[] args) throws Exception {
        Router.get("/api/helloworld", (req, res) -> "hello world!");
        Router.get("/api/hello", (req, res) -> "hello " + req.getValues("name"));
        Router.get("/api/pi", (req, resp) -> String.valueOf(Math.PI));
        
        String path = staticFilesPath != null ? staticFilesPath : "target/classes/webroot";
        new WebServer(path).start();
    }

    public WebServer(String staticFilesPath) {
        this.staticFilesPath = staticFilesPath;
        this.fileHandler = new FileHandler(staticFilesPath);
        this.apiHandler = new ApiHandler();
    }

    public void start() throws Exception {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Server started on port " + port);
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) throws Exception {
        try {
            RequestHandler handler = new RequestHandler(clientSocket, fileHandler, apiHandler);
            handler.handleRequest();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }

    public static void staticfiles(String path) {
        staticFilesPath = path;
    }

    public static String getStaticFilesPath() {
        return staticFilesPath;
    }
}