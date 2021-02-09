package com.chat.server;

import com.chat.auth.AuthenticationService;
import com.chat.auth.BasicAuthenticationService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer implements Server {
    private final Set<ClientHandler> clients;
    private final AuthenticationService authenticationService;
    private static Logger logger = Logger.getLogger(ChatServer.class);


    public ChatServer() {
        try {
            logger.debug("Server is starting up...");
            ServerSocket serverSocket = new ServerSocket(8888);
            clients = new HashSet<>();
            authenticationService = new BasicAuthenticationService();
            logger.debug("Server is started up...");
            while (true) {
                logger.debug("Server is listening for clients...");
                Socket socket = serverSocket.accept();
                logger.debug("Client accepted: " + socket);
                new ClientHandler(this, socket, logger);
            }
        } catch (IOException e) {
            logger.debug("Server start error " + e);
            throw new RuntimeException("SWW", e);
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) {
        clients.forEach(client -> client.sendMessage(message));
    }

    @Override
    public void privateMessage(String nickname, String message) {
        clients.stream()
                .filter(clientHandler -> clientHandler.getName().equals(nickname))
                .forEach(clientHandler -> clientHandler.sendMessage(message));
    }

    @Override
    public synchronized boolean isLoggedIn(String nickname) {
        return clients.stream()
                .filter(clientHandler -> clientHandler.getName().equals(nickname))
                .findFirst()
                .isPresent();
    }

    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
}
