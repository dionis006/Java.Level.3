package com.chat.server;

import com.chat.LogService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler {
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    private final File file;
    private final LogService logService;


    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            file = new File(
                    "E:\\Rustam\\Java\\=Обучение=\\Java.Level.3\\Lesson 3\\src\\com\\chat\\ServerLog.txt"
            );
            logService = new LogService();
            doListen();
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            logService.doBufferedWriter(file, message);
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void sendLog() {
        StringBuilder stringBuilder = logService.doBufferedReader(100);
        try {
            String str = new String(stringBuilder.toString()
                    .getBytes("ISO-8859-1"), "UTF8");
            out.writeUTF(str);
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public String getName() {
        return name;
    }

    private void doListen() {
        new Thread(() -> {
            try {
                doAuth();
                sendLog();
                receiveMessage();
            } catch (Exception e) {
                throw new RuntimeException("SWW", e);
            } finally {
                server.unsubscribe(this);
            }
        }).start();
    }

    private void doAuth() {
        try {
            while (true) {
                String credentials = in.readUTF(); // -auth n1@mail.com 1
                if (credentials.startsWith("-auth")) {
                    String[] credentialValues = credentials.split("\\s");
                    String newNick = server.getAuthenticationService()
                            .doAuth(credentialValues[1], credentialValues[2]);
                    if (newNick != null) {
                        if (!server.isLoggedIn(newNick)) {
                            sendMessage("cmd auth: Status OK");
                            name = newNick;
                            server.broadcastMessage(name + " is logged in.");
                            server.subscribe(this);
                            break;
                        } else {
                            sendMessage("Current user is already logged in.");
                        }
                    } else {
                        sendMessage("No a such user by email and password.");
                    }
                } else {
                    receiveMessage(credentials);
                }
            }
        } catch (
                IOException e) {
            throw new RuntimeException("SWW", e);
        }

    }

    /**
     * Receives input data from {@link ClientHandler#in} and then broadcast via {@link Server#broadcastMessage(String)}
     */
    private void receiveMessage() {
        try {
            while (true) {
                System.out.println("receiveMessage read...");
                String message = in.readUTF();
                System.out.println("receiveMessage read");
                if (message.equals("-exit")) {
                    return;
                }

                if (message.startsWith("/w")) {
                    String[] partMessages = message.split("\\s");
                    server.privateMessage(partMessages[1], partMessages[2]);
                }

                server.broadcastMessage(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    private void receiveMessage(String message) {
        if (message.equals("-exit")) {
            return;
        }

        if (message.startsWith("/w")) {
            String[] partMessages = message.split("\\s");
            server.privateMessage(partMessages[1], partMessages[2]);
        } else {
            server.broadcastMessage(message);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(socket, that.socket) &&
                Objects.equals(in, that.in) &&
                Objects.equals(out, that.out) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, socket, in, out, name);
    }
}
