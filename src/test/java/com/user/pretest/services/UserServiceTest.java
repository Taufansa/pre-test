package com.user.pretest.services;

import com.user.pretest.dao.UserCreateDao;
import com.user.pretest.dao.UserUpdateDao;
import com.user.pretest.entities.User;
import com.user.pretest.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private String id;

    @BeforeEach
    public void setup() {
        id = "fskjno";
        user = User.builder()
                .id(id)
                .name("test mock")
                .age(100)
                .contacts(new ArrayList<>())
                .build();
    }

    @DisplayName("Test fetch all users")
    @Test
    public void testFetchAllUser() {
        User user2 = User.builder()
                .id("idcojwpq")
                .name("test mock 2")
                .age(60)
                .contacts(new ArrayList<>())
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        Assertions.assertSame(userRepository.findAll(), userService.fetchAllUsers().getBody());
    }

    @DisplayName("Test fetch user by Id")
    @Test
    public void testFetchUserById(){
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        Assertions.assertSame(userRepository.findById(id), userService.fetchUserById(id).getBody());
    }

    @DisplayName("Test create new user")
    @Test
    public void testCreateUser(){
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        UserCreateDao userCreateDao = UserCreateDao.builder()
                .name("test mock")
                .age(100)
                .build();

        System.out.println(userRepository.saveAndFlush(user));
        Assertions.assertEquals(HttpStatus.CREATED, userService.createUser(userCreateDao).getStatusCode());
//        Assertions.assertSame(userRepository.saveAndFlush(user), userService.createUser(userCreateDao).getBody());
    }

    @DisplayName("Test update user")
    @Test
    public void testUpdateUser() {
        user.setName("test mock 2");
        user.setAge(10);

        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        UserUpdateDao userUpdateDao = UserUpdateDao.builder()
                .id(id)
                .name("test mock 2")
                .age(18)
                .build();

        Assertions.assertEquals(HttpStatus.OK, userService.updateUser(userUpdateDao).getStatusCode());
        Assertions.assertSame(userRepository.saveAndFlush(user), userService.updateUser(userUpdateDao).getBody());
    }

    @DisplayName("Test delete user")
    @Test
    public void testDestroyUser(){
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        Assertions.assertSame(HttpStatus.OK, userService.deleteUser(id).getStatusCode());
    }
}
