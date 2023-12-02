package com.example.BikeApplication.controller;

import com.example.BikeApplication.domain.User;
import com.example.BikeApplication.service.UserAlreadyExistsException;
import com.example.BikeApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") //angular link
@RestController
@RequestMapping("/user") //used to map to angular
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
//
//        User newUser = userService.registerUser(user);
//        logger.info("New user registered: {}", newUser);//System.out.println(newUser);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        try {
            User newUser = userService.registerUser(user);
            logger.info("New user registered: {}", newUser);
            //System.out.println(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED); }
        catch (ConstraintViolationException e) {
            // if there are validation errors in user fields
            logger.error("Validation error while registering user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        catch (UserAlreadyExistsException e) {
            // if user with same email or phone number already exists
            logger.error("User already exists error while registering user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); }
        catch (Exception e) { // for all other runtime exceptions
            // logger.error("Unknown error while registering user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> credentials) {
        logger.info("Login attempt for user with email: {}", credentials.get("email"));
        //System.out.println(credentials);
        User user = userService.loginUser(credentials.get("email"), credentials.get("password"));
        logger.info("User with email {} has logged in", credentials.get("email"));
        //System.out.println(user);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User updatedUserDetails) {
        User updatedUser = userService.updateUser(userId, updatedUserDetails);
        return updatedUser;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return user;
    }


}

