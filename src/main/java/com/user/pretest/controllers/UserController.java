package com.user.pretest.controllers;

import com.user.pretest.dao.UserCreateDao;
import com.user.pretest.dao.UserUpdateDao;
import com.user.pretest.entities.User;
import com.user.pretest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> fetchUsers(){
        return userService.fetchAllUsers();
    }

    @GetMapping("/{userId}/detail")
    public ResponseEntity<Optional<User>> fetchUserById(@PathVariable(name = "userId") String userId){
        return userService.fetchUserById(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDao userCreateDao){
        return userService.createUser(userCreateDao);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDao userUpdateDao){
        return userService.updateUser(userUpdateDao);
    }

    @DeleteMapping("/{userId}/destroy")
    public ResponseEntity<User> destroyUser(@PathVariable(name = "userId") String userId){
        return userService.deleteUser(userId);
    }

}
