package com.user.pretest.services;

import com.user.pretest.dao.UserContactCreateDao;
import com.user.pretest.dao.UserContactUpdateDao;
import com.user.pretest.entities.UserContact;
import com.user.pretest.repositories.UserContactRepository;
import com.user.pretest.utils.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactService {

    @Autowired
    private UserContactRepository userContactRepository;

    public ResponseEntity<List<UserContact>> fetchAllUserContacts(){
        return new ResponseEntity<>(userContactRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Optional<UserContact>> fetchUserContactById(String id) {
        Optional<UserContact> userContact = userContactRepository.findById(id);

        if (!userContact.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userContact, HttpStatus.OK);
    }

    public ResponseEntity<UserContact> createUserContact(UserContactCreateDao userContactCreateDao){
        UserContact newUserContact = new UserContact();

        newUserContact.setId(generateUserContactId());
        newUserContact.setAddress(userContactCreateDao.getAddress());
        newUserContact.setUser(userContactCreateDao.getUser());

        return new ResponseEntity<>(userContactRepository.saveAndFlush(newUserContact), HttpStatus.CREATED);
    }

    public ResponseEntity<UserContact> updateUserContact(UserContactUpdateDao userContactUpdateDao){
        Optional<UserContact> userContact = userContactRepository.findById(userContactUpdateDao.getId());

        if (!userContact.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userContact.get().setAddress(userContactUpdateDao.getAddress());
        userContact.get().setUser(userContactUpdateDao.getUser());

        return new ResponseEntity<>(userContactRepository.saveAndFlush(userContact.get()), HttpStatus.OK);
    }

    public ResponseEntity<UserContact> deleteUserContact(String id){
        Optional<UserContact> userContact = userContactRepository.findById(id);

        if (!userContact.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userContactRepository.delete(userContact.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
    private String generateUserContactId(){
        Boolean isIdUsed = false;
        String randomId = "";
        while (!isIdUsed) {
            randomId = UtilClass.randomString();
            Optional<UserContact> userContact = userContactRepository.findById(randomId);
            if (userContact.isEmpty()) {
                isIdUsed = true;
            }
        }
        return randomId;
    }
}
