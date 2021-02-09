package com.chat.auth;

public interface AuthenticationService {
    String doAuth(String login, String password);
}
