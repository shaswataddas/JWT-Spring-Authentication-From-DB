package com.jwt.authentication.jwtAuthentication.controllers;

import com.jwt.authentication.jwtAuthentication.entities.User;
import com.jwt.authentication.jwtAuthentication.dto.JwtRequest;
import com.jwt.authentication.jwtAuthentication.dto.JwtResponse;
import com.jwt.authentication.jwtAuthentication.security.JwtHelper;
import com.jwt.authentication.jwtAuthentication.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/remove/{userId}")
    public String removeUser(@PathVariable int userId){
        User tempUser = userService.findById(userId);
        if(tempUser == null){
            throw new RuntimeException("User Id not found - "+userId);
        }
        userService.deleteById(userId);
        return "User "+tempUser.getFirstName()+" is successfully removed from the system.";
    }

    @GetMapping("/forgotPassword/{emailId}")
    public ResponseEntity<String> userForgotPassword(@PathVariable String emailId){
        return userService.forgotPassword(emailId);
    }

    @PutMapping("/secure/resetPassword/{securityToken}")
    public ResponseEntity<String> userResetPassword(@RequestBody User user, @PathVariable String securityToken) {
        return userService.resetPassword(user, securityToken);
    }
}
