package ba.pehli.facebook.controller;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.WebApplicationContext;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.domain.User;
import ba.pehli.facebook.service.PostService;
import ba.pehli.facebook.service.UserService;

/**
 * Razne vrste testova na kontroleru uključujući unit testove i integration testove
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations="classpath:spring/application-config.xml"),
				   @ContextConfiguration(locations="/WEB-INF/mvc-config.xml")
})		
@WebAppConfiguration
public class UserControllerTest {
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private FilterChainProxy filter;
		
	MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(filter)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();			
	}
	
	@Test
	public void testLoginRequest() throws Exception{
		mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML))
			   .andExpect(status().isOk())
			   .andExpect(view().name("login"));
	}
	
	@Test
	public void testLoginFunction() throws Exception{
		UserController controller = new UserController();
		ExtendedModelMap model = new ExtendedModelMap();
		Locale locale = Locale.US;
		
		String view = controller.login(model, null, null, locale);
		assertNotNull(model.get("user"));
		
	}
	
	@Test
	public void testProtectedUrlRequest() throws Exception{
		mockMvc.perform(get("/home"))
				.andExpect(redirectedUrl("http://localhost/login"));
		
		mockMvc.perform(get("/like"))
			.andExpect(unauthenticated());
		
	}
	
	@Test
	public void testProtectedUrlRequestWithUserDetails() throws Exception{
		mockMvc.perform(get("/home").with(user("jane.doe").password("jane")))
				.andExpect(status().isOk());
		
		mockMvc.perform(get("/like"))
			.andExpect(unauthenticated());
		
	}
	
	@Test
	@WithMockUser(username="john.doe",password="john")
	public void testProtectedUrlRequestWithUserDetails2() throws Exception{
		mockMvc.perform(get("/home"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testLogin() throws Exception{
		mockMvc.perform(formLogin().user("jane.doe").password("jane"))
				.andExpect(redirectedUrl("/home"));
		
		mockMvc.perform(formLogin().user("jane.doe").password("jane"))
			.andExpect(authenticated());
	}
	
	@Test
	@WithMockUser(username="john.doe",password="john")
	public void trestHomeFunction(){
		User user = new User(-1,"john.doe","pass","John","Doe",new Date(), "M");
		List<Post> posts = new ArrayList<Post>();
		for (int i=1; i<=10; i++){
			Post post = new Post();
			post.setId(0-i);
			post.setText("Text " + i);
			posts.add(post);
		}
		
		
		UserController controller = new UserController();
		
		PostService postService = Mockito.mock(PostService.class);
		Mockito.when(postService.findAll()).thenReturn(posts);
		Field field = ReflectionUtils.findField(UserController.class, "postService");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, controller, postService);
		
		UserService userService = Mockito.mock(UserService.class);
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
		field = ReflectionUtils.findField(UserController.class, "userService");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, controller, userService);
		
		MockHttpServletRequest req = new MockHttpServletRequest();
		ExtendedModelMap model = new ExtendedModelMap();
		String view = controller.home(model, req, new Date(), new User(), "", "");
		
		assertEquals(view, "home");
		assertNotNull(model.get("user"));
		assertNotNull(model.get("posts"));
		
		assertEquals(model.get("user"), user);
		assertEquals(((List<Post>)model.get("posts")).size(), posts.size());
	}
	

}
