package com.chat;

import com.chat.database.UserRepository;
import com.chat.entity.User;

public class Main {
    public static void main(String[] args) {

//        UserRepository repository = new UserRepository();
//        repository.create(new User("n1", "n1@mail.com", "1"));
//        repository.create(new User("n2", "n2@mail.com", "2"));
//        repository.create(new User("n3", "n3@mail.com", "3"));
//        repository.create(new User("n4", "n4@mail.com", "4"));
//        repository.create(new User("n5", "n5@mail.com", "5"));

        UserRepository repository = new UserRepository();
        System.out.println(repository.findAll());



    }
}
