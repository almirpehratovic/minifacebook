package ba.pehli.facebook.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.pehli.facebook.domain.ProfilePicture;
import ba.pehli.facebook.domain.User;
import ba.pehli.facebook.repository.ProfilePictureRepository;
import ba.pehli.facebook.repository.UserRepository;

@Service("userService")
@Transactional
public class UserServiceJpaImpl implements UserService{
	
	@PersistenceContext		// ubacivanje entity managera kojim upravlja kontejner
	EntityManager em;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	ProfilePictureRepository profileRepo;

	@Override
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return em.createQuery("select u from User u").getResultList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public User findByUsername(String username){
		List<User> users = repo.findByUsername(username);
		if (users.size() > 0){
			return users.get(0);
		} 
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findByUsernameContaining(String text) {
		return repo.findByUsernameContaining(text);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> findByFirstName(String firstName) {
		return repo.findByFirstName(firstName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> findByLastName(String lastName) {
		return findByLastName(lastName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> findByBirthDateBetween(Date date1, Date date2) {
		return findByBirthDateBetween(date1, date2);
	}

	@Override
	public User save(User user) {
		return repo.save(user);
	}

	@Override
	public void delete(User user) {
		repo.delete(user);
		
	}

	@Override
	public ProfilePicture findProfilePictureById(int id) {
		return (ProfilePicture) em.createQuery("select p from ProfilePicture p where p.id = :id").setParameter("id", id).getSingleResult();
	}

	@Override
	public List<ProfilePicture> findAllProfilePicturesByUser(User user) {
		return profileRepo.findByUser(user);
	}
	
	

}
