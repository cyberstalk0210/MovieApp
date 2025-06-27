package com.example.movieapp.controller;

import com.example.movieapp.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping
    public String signIn(@RequestBody User user){
    return null;
    }

}
