package ba.pehli.facebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.service.PostService;
import ba.pehli.facebook.service.UserService;

@RestController
@RequestMapping("/api")
public class WebServiceController {
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@RequestMapping(value="/check",method=RequestMethod.GET)
	public boolean isUsernameAvailable(String username){
		return userService.findByUsername(username) != null;
	}
	
	@GetMapping("/posts")
	public List<Post> getPosts(){
		return postService.findAll();
	}
}
