package com.sarfaraz.management.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sarfaraz.management.model.User;

@SpringBootTest
class UserServiceTest {
	@Autowired
	private UserService service;
	@MockBean
	private User user;
	private long uid = 364;

	@Test
	void testUserService() {
//		User list = service.getAllwithprojectandroles(uid);
//
//		System.out.println(list);
//		System.out.println(list.getProject());
//		System.out.println(list.getRoles());

	}

}
