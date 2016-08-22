package ba.pehli.facebook.service;

import java.util.List;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.domain.User;

public interface PostService {
	Post findById(int id);
	List<Post> findAll();
	List<Post> findAllByPoster(User poster);
	
	Post save(Post post);
	void delete(Post post);
}
