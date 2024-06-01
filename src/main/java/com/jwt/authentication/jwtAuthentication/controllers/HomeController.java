package com.jwt.authentication.jwtAuthentication.controllers;

import com.jwt.authentication.jwtAuthentication.entities.User;
import com.jwt.authentication.jwtAuthentication.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    // endpoint - http://localhost:8081/home/user
    public List<User> getUser(){
        return this.userService.getUsers();
    }

    @GetMapping("/current-user")
    public String getCurrentUser(Principal principal){
        return principal.getName()+" is currently logged in";
    }

}
