package ba.pehli.facebook.service;

import java.util.Date;
import java.util.List;

import ba.pehli.facebook.domain.ProfilePicture;
import ba.pehli.facebook.domain.User;

public interface UserService {
	List<User> findAll();
	User findByUsername(String username);
	List<User> findByUsernameContaining(String text);
	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);
	List<User> findByBirthDateBetween(Date date1, Date date2);
	
	User save(User user);
	void delete(User user);
	
	ProfilePicture findProfilePictureById(int id);
	List<ProfilePicture> findAllProfilePicturesByUser(User user);
}
