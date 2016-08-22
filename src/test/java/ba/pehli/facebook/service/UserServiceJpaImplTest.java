package ba.pehli.facebook.service;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ba.pehli.facebook.domain.User;
import ba.pehli.facebook.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/application-config.xml")
@Rollback
@Transactional(transactionManager="transactionManager")
public class UserServiceJpaImplTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository repo;

	@Test
	public void testFindAll() {
		List<User> users = userService.findAll();
		assertEquals(users.size()>0, true);
	}
	
	@Test
	public void testFindByUsername(){
		assertNull(userService.findByUsername("kdfkh"));
		assertNotNull(userService.findByUsername("jane.doe"));
	}
	
	@Test
	public void testFindByUsernameContaining(){
		List<User> users = userService.findByUsernameContaining("jane");
		assertEquals(users.size()>0, true);
		
		users = userService.findByUsernameContaining("betmen");
		assertEquals(users.size()>0, false);
	}
	
	@Test
	public void testSaveDelete(){
		User user = new User(0, "test", "test", "Test", "Test", new Date(), "M");
		User db = userService.save(user);
		
		assertNotEquals(user.getId(), db.getId());
		assertEquals(user.getUsername(), db.getUsername());
		
		db.setUsername("test2");
		user = userService.save(db);
		
		assertEquals(user.getId(), db.getId());
		assertEquals(user.getUsername(), "test2");
		
		List<User> users = userService.findByUsernameContaining("test2");
		assertTrue(users.size() > 0);
		
		userService.delete(user);
		
		users = userService.findByUsernameContaining("test2");
		assertTrue(users.size() == 0);
		
	}
	
	@Test
	public void testFindFirstX() throws InterruptedException, ExecutionException{
		for (int i=0; i<10; i++){
			User user = new User(-1, "test.user" + i,"test","testuser","user",new Date(), "M");
			userService.save(user);
		}
		
		List<User> users = repo.findFirst2ByFirstName("testuser");
		assertEquals(users.size(), 2);
		
		Future<List<User>> fut = repo.findFirst3ByFirstName("testuser");
		users = fut.get();
		assertEquals(users.size(), 3);
		
	}
	


}
