package com.example.BikeApplication.service;

import com.example.BikeApplication.domain.User;
import com.example.BikeApplication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private Validator validator; //to validate the @NotBlank,email etc
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public User registerUser(User user) throws ConstraintViolationException, UserAlreadyExistsException {
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    if (!violations.isEmpty()) { throw new ConstraintViolationException(violations); }
    logger.info("Registering user: {}", user);//System.out.println(user);
     User existingUser = userRepository.findByUserName(user.getUserName());
     if (existingUser != null) { logger.warn("Username already exists: {}",
             user.getUserName());
         throw new UserAlreadyExistsException("Username already exists"); }

    if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null) {
        logger.warn("Email already exists: {}", user.getEmail());
        throw new UserAlreadyExistsException("Email already exists");
    }

    if (user.getPhone() != null && userRepository.findByPhone(user.getPhone()) != null) {
        logger.warn("Phone number already exists: {}", user.getPhone());
        throw new UserAlreadyExistsException("Phone number already exists");
    }
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    logger.info("User saved: {}", savedUser);
    return savedUser;
}
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        // Check if user exists and password is correct
        if(user != null && password.equals(user.getPassword())) {
            logger.info("User logged in: {}", user);
            return user;
        }
        else {
            logger.warn("Invalid login attempt for login: {}", email);
            return null;
        }
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, User updatedUserDetails) {
        User userToUpdate = userRepository.findByUserId(userId);
        logger.debug("Updating user with userId '{}': {}", userId, userToUpdate);
        //System.out.println(userToUpdate);
        userToUpdate.setUserName(updatedUserDetails.getUserName());
        userToUpdate.setPhone(updatedUserDetails.getPhone());
        userToUpdate.setEmail(updatedUserDetails.getEmail());
        userToUpdate.setPassword(updatedUserDetails.getPassword());
        userToUpdate.setPaymentMode(updatedUserDetails.getPaymentMode());
        userToUpdate.setAddress(updatedUserDetails.getAddress());
        userToUpdate.setRole(updatedUserDetails.getRole());
        User updatedUser = userRepository.save(userToUpdate);
        logger.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    public User getUserById(String userId) {
        User user = userRepository.findByUserId(userId);
        logger.info("Returning user with userId '{}': {}", userId, user);
        return user;
    }
}
