package org.example.springbootxml_json.controller;

import org.example.springbootxml_json.model.User;
import org.example.springbootxml_json.model.Users;
import org.example.springbootxml_json.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    private UsersRepository userRepository;
   // Endpoint to save single users
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User created successfully", "user", savedUser));
    }

    // Endpoint to save multiple users
    @PostMapping(value = "/multiple", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity createUsers(@RequestBody Users users) {
        List<User> savedUsers = userRepository.saveAll(users.getUsers());
        return ResponseEntity.status(HttpStatus.CREATED).
                body(Map.of("message", "User created successfully", "user", savedUsers));
    }
//Endpoint to fetch by using id
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getUserById(@PathVariable String id) {
        Optional<User> user= userRepository.findById(id);
        if(user.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).
                    body(user.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found with this id "+id);
        }
    }
//Endpoint to fetch all users
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getAllUsers() {
        List<User> userList = userRepository.findAll();
        Users users = new Users();
        users.setUsers(userList); // Set the users list

        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(users);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    //Endpoint to delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with this id "+id + " has been delete successfuly");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id "+id+" not found");
        }
    }

    //Endpoint to delete all users
    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        List<User> users=userRepository.findAll();
        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User list is empty");
        }else {
            userRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("user",users , "message" ,"All users are deleted successfully"));
        }
    }

    //Endpoint to update user by ID
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity updateUserById(@PathVariable String id, @RequestBody User userDetails) {
         Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            User existingUser = user.get();
            existingUser.setUsername(userDetails.getUsername());
            User updatedUser=userRepository.save(existingUser);
           // return ResponseEntity.ok(updatedUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "User with this ID " + id + " has been updated successfully.",
                            "user", updatedUser));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this id "+id);
        }
    }
}
