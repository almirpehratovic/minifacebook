package ba.pehli.facebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.domain.User;
import ba.pehli.facebook.repository.PostRepository;

@Service("postService")
public class PostServiceJpaImpl implements PostService{
	
	@Autowired
	private PostRepository repo;
	
	@Override
	public Post findById(int id){
		return repo.findOne(id);
	}

	@Override
	public List<Post> findAll() {
		//List<Post> posts = new ArrayList<Post>();
		//repo.findAll().forEach(e -> posts.add(e));
		//return posts;
		return repo.findAllByOrderByIdDesc();
	}

	@Override
	public List<Post> findAllByPoster(User poster) {
		return repo.findByPoster(poster);
	}

	@Override
	public Post save(Post post) {
		return repo.save(post);
	}

	@Override
	public void delete(Post post) {
		repo.delete(post);
		
	}

}
