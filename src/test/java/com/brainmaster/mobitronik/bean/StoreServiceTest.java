package com.brainmaster.mobitronik.bean;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.bean.service.UserService;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.mobitronik.model.User;
import com.brainmaster.mobitronik.model.UserStore;
import com.brainmaster.util.helper.uuid.UUIDHelper;
import static org.junit.Assert.*;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class StoreServiceTest {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(StoreServiceTest.class);

	@Autowired
	private StoreService storeService;

	@Autowired
	private UserService userService;

	private Store parent;
	private Store child;
	private UUID parentUuid;

	@Before
	public void initStore() {
		parentUuid = UUIDHelper.objectToUUID(UUID.randomUUID());
		parent = new Store(parentUuid, ArrayUtils.toObject("Jln. Panjang"
				.getBytes()), "12345");
		storeService.addStore(parent);
		parent = storeService.find(parentUuid);
		child = new Store(UUIDHelper.objectToUUID(UUID.randomUUID()),
				ArrayUtils.toObject("Jln. Panjang".getBytes()), "12345");
		child.setParentStore(parent);
		storeService.addStore(child);
		parent.getChildStores().add(child);
		child = new Store(UUIDHelper.objectToUUID(UUID.randomUUID()),
				ArrayUtils.toObject("Jln. Panjang".getBytes()), "12345");
		child.setParentStore(parent);
		storeService.addStore(child);
		parent.getChildStores().add(child);
	}

	@Test
	public void itShouldInsertAChildFromParent() {
		System.out.println("parent Store :" + parentUuid);
		for (Store store : storeService.findAll()) {
			System.out.println("Store : " + store.getUuid());
			Store internalStore = storeService.find(store.getUuid());
			for (Store child : internalStore.getChildStores()) {
				System.out.println("Child : " + child.getUuid());
			}
		}
	}

	@Test
	public void itShouldInsertUserStore() {
		System.out.println("parent Store :" + parentUuid);
		User user = new User("ada@ada", ArrayUtils.toObject("haha".getBytes()),
				"ucup");
		for (UserStore userStore : user.getStores()) {
			userStore.setStore(parent);
		}
		userService.addUser(user);
		assertTrue(userService.find(user.getEmailAddress()).getStores().size() == 1);
	}
}
