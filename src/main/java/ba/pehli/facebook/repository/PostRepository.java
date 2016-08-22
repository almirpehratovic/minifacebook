package ba.pehli.facebook.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.domain.User;

public interface PostRepository extends CrudRepository<Post, Integer>{
	List<Post> findAllByOrderByIdDesc();
	List<Post> findByPoster(User poster);
}
