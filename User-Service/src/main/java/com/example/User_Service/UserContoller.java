package com.example.User_Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserContoller {

    private List<User> users;

    @GetMapping("/")
    public List<User> getUsers() {
        if(users==null || users.isEmpty()){
            users=new ArrayList<>();
            users.add(new User(1, "a"));
            users.add(new User(2, "b"));
            users.add(new User(3, "c"));
        }

        return users;
    }

}
