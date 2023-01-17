package com.user.pretest.services;

import com.user.pretest.dao.UserCreateDao;
import com.user.pretest.dao.UserUpdateDao;
import com.user.pretest.entities.User;
import com.user.pretest.repositories.UserRepository;
import com.user.pretest.utils.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<User>>fetchAllUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Optional<User>> fetchUserById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(UserCreateDao userCreateDao){
        User newUser = new User();
        newUser.setId(generateUserId());
        newUser.setName(userCreateDao.getName());
        newUser.setAge(userCreateDao.getAge());

        return new ResponseEntity<>(userRepository.saveAndFlush(newUser), HttpStatus.CREATED);
    }

    public ResponseEntity<User> updateUser(UserUpdateDao userUpdateDao){
        Optional<User> user = userRepository.findById(userUpdateDao.getId());

        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        user.get().setName(userUpdateDao.getName());
        user.get().setAge(userUpdateDao.getAge());

        return new ResponseEntity<>(userRepository.saveAndFlush(user.get()), HttpStatus.OK);
    }

    public ResponseEntity<User> deleteUser(String id){
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
    private String generateUserId(){
        Boolean isIdUsed = false;
        String randomId = "";
        while (!isIdUsed) {
            randomId = UtilClass.randomString();
            Optional<User> user = userRepository.findById(randomId);
            if (user.isEmpty()) {
                isIdUsed = true;
            }
        }
        return randomId;
    }
}
