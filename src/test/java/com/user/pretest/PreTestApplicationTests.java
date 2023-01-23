package com.user.pretest;

import com.user.pretest.dao.UserCreateDao;
import com.user.pretest.dao.UserUpdateDao;
import com.user.pretest.entities.User;
import com.user.pretest.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
class PreTestApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void fetchAllUsersTest() {
		var response = userService.fetchAllUsers();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void fetchUserByIdTest() {
		var userCreateDao = UserCreateDao.builder()
				.name("User Test")
				.age(66)
				.build();

		var user = userService.createUser(userCreateDao);

		var response = userService.fetchUserById(user.getBody().getId());

		Assertions.assertEquals(response.getBody().get().getId(), user.getBody().getId());
	}

	@Test
	public void createUserTest() {
		var userCreateDao = UserCreateDao.builder()
				.name("User Test 2")
				.age(66)
				.build();

		var user = userService.createUser(userCreateDao);

		Assertions.assertNotEquals(null, user.getBody().getId());
	}

	@Test
	public void updateUserTest() {
		var userCreateDao = UserCreateDao.builder()
				.name("User Test 3")
				.age(76)
				.build();

		var user = userService.createUser(userCreateDao);

		var userUpdateDto = UserUpdateDao.builder()
				.id(user.getBody().getId())
				.name("User 3")
				.age(17)
				.build();

		var userUpdated = userService.updateUser(userUpdateDto);

		Assertions.assertNotEquals(user.getBody().getName(), userUpdated.getBody().getName());
		Assertions.assertNotEquals(user.getBody().getAge(), userUpdated.getBody().getAge());
	}

	@Test
	public void deleteUserTest(){
		var userCreateDao = UserCreateDao.builder()
				.name("User Test 4")
				.age(96)
				.build();

		var user = userService.createUser(userCreateDao);

		userService.deleteUser(user.getBody().getId());

		var checkUser = userService.fetchUserById(user.getBody().getId());

		Assertions.assertEquals(null, checkUser.getBody());

	}

}
