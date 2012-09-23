package com.brainmaster.mobitronik.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.bean.service.UserService;
import com.brainmaster.mobitronik.dto.UserData;
import com.brainmaster.mobitronik.model.User;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {

	private static final Logger log = LoggerFactory
			.getLogger(UserServiceTest.class);

	private User user;
	private List<User> userList;
	private int prevUser;

	@Autowired
	private UserService userService;

	@Before
	public void initUser() {
		prevUser = userService.findAll().size();
		user = new User("ucup@mail.com", org.apache.commons.lang3.ArrayUtils
				.toObject("password".getBytes()), "ucup pasti");
		userList = new ArrayList<User>();
		while (userList.size() < 10) {
			userList.add(new User("ucup" + userList.size() + "@mail.com",
					org.apache.commons.lang3.ArrayUtils.toObject("password"
							.getBytes()), "ucup pasti " + userList.size()));
		}
	}

	@Test
	public void testShouldInsertANewUser() {
		userService.addUser(user);
		User data = userService.find(user.getEmailAddress());
		assertNotNull(data);
	}

	//	@Test(expected = Exception.class)
	@Test(expected = EntityNotFoundException.class)
	public void testShouldDeleleTheUser() {
		userService.removeUser(UserData.convertUser(user));
		User data = userService.find(user.getEmailAddress());
		assertNull(data);
	}

	@Test
	public void testShouldFindAllUser() {
		for (User user : userList)
			userService.addUser(user);
		log.info("user size = {}", userService.findAll().size());
		assertTrue(userService.findAll().size() == 10 + prevUser);
	}

}
