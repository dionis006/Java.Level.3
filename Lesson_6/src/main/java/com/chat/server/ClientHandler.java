package com.chat.server;

import com.chat.LogService;
import org.apache.log4j.Logger;

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


    public ClientHandler(Server server, Socket socket, Logger logger) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            file = new File(
                    "E:\\Rustam\\Java\\=Обучение=\\Java.Level.3\\Lesson_6\\src\\main\\java\\com\\chat\\ServerLog.txt"
            );
            logService = new LogService();
            doListen(logger);
        } catch (IOException e) {
            logger.debug("Server start error " + e);
            throw new RuntimeException("SWW", e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            if (message != "cmd auth: Status OK") {
                logService.doBufferedWriter(file, message);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void sendLog(Logger logger) {
        StringBuilder stringBuilder = logService.doBufferedReader(100);
        try {
            String str = new String(stringBuilder.toString()
                    .getBytes("ISO-8859-1"), "UTF8");
            out.writeUTF(str);
        } catch (IOException e) {
            logger.debug("Server sendLog error: " + e);
            throw new RuntimeException("SWW", e);
        }
    }

    public String getName() {
        return name;
    }

    private void doListen(Logger logger) {
        new Thread(() -> {
            try {
                doAuth(logger);
                sendLog(logger);
                receiveMessage(logger);
            } catch (Exception e) {
                logger.debug("Server doListen error: " + e);
                throw new RuntimeException("SWW", e);
            } finally {
                server.unsubscribe(this);
            }
        }).start();
    }

    private void doAuth(Logger logger) {
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
        } catch (IOException e) {
            logger.debug("Server doAuth error: " + e);
            throw new RuntimeException("SWW", e);
        }

    }

    /**
     * Receives input data from {@link ClientHandler#in} and then broadcast via {@link Server#broadcastMessage(String)}
     */
    private void receiveMessage(Logger logger) {
        try {
            while (true) {
//                System.out.println("receiveMessage read...");
                logger.debug("Server receiveMessage read...");
                String message = in.readUTF();
//                System.out.println("receiveMessage read");
//                logger.debug(this.name + "send message");
                if (message.equals("-exit")) {
                    logger.debug(this.name + " left chat");
                    return;
                }
                if (message.startsWith("/w")) {
                    String[] partMessages = message.split("\\s");
                    logger.debug(this.name + " send private message");
                    server.privateMessage(partMessages[1], partMessages[2]);
                }
                logger.debug(this.name + " send message");
                server.broadcastMessage(message);
            }
        } catch (IOException e) {
            logger.debug("Server receiveMessage error: " + e);
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
