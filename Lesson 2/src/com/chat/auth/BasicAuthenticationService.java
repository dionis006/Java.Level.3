package com.chat.auth;

import com.chat.database.UserRepository;
import com.chat.entity.User;

import java.util.List;
import java.util.Optional;

public class BasicAuthenticationService implements AuthenticationService {

    @Override
    public Optional<User> doAuth(String email, String password) {
        UserRepository repository = new UserRepository();
        List<User> user = repository.find(email);
        for (User value : user) {
            if (value.getEmail().equals(email) && value.getPassword().equals(password)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

}
