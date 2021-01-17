package com.chat.auth;

import com.chat.database.UserRepository;
import com.chat.entity.User;

import java.util.List;

public class BasicAuthenticationService implements AuthenticationService {
    private static List<User> users;


    @Override
    public String doAuth(String email, String password) {
        UserRepository repository = new UserRepository();
        List<User> user = repository.find(email);
        for (User value : user) {
            if (value.getEmail().equals(email) && value.getPassword().equals(password)) {
                String nickName = value.getNickname();
                return nickName;
            }
        }
        return null;
    }

}
