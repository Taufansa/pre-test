package com.user.pretest.controllers;

import com.user.pretest.dao.UserContactCreateDao;
import com.user.pretest.dao.UserContactUpdateDao;
import com.user.pretest.entities.UserContact;
import com.user.pretest.services.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user_contact")
public class UserContactController {

    @Autowired
    private UserContactService userContactService;

    @GetMapping("/all")
    public ResponseEntity<List<UserContact>> fetchUserContacts(){
        return userContactService.fetchAllUserContacts();
    }

    @GetMapping("/{userContactId}/detail")
    public ResponseEntity<Optional<UserContact>> fetchUserContactById(@PathVariable(name = "userContactId") String userContactId){
        return userContactService.fetchUserContactById(userContactId);
    }

    @PostMapping("/create")
    public ResponseEntity<UserContact> createUserContact(@RequestBody UserContactCreateDao userContactCreateDao){
        return userContactService.createUserContact(userContactCreateDao);
    }

    @PutMapping("/update")
    public ResponseEntity<UserContact> updateUserContact(@RequestBody UserContactUpdateDao userContactUpdateDao){
        return userContactService.updateUserContact(userContactUpdateDao);
    }

    @DeleteMapping("/{userId}/destroy")
    public ResponseEntity<UserContact> destroyUserContact(@PathVariable(name = "userContactId") String userContactId){
        return userContactService.deleteUserContact(userContactId);
    }
}
