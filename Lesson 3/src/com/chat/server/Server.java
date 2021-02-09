package com.chat.server;

import com.chat.auth.AuthenticationService;

public interface Server {
    void broadcastMessage(String message);
    void privateMessage(String nickname, String message);
    boolean isLoggedIn(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
