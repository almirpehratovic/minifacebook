package ba.pehli.facebook.repository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import ba.pehli.facebook.domain.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	List<User> findByUsername(String username);
	List<User> findByUsernameContaining(String text);
	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);
	List<User> findByBirthDateBetween(Date date1, Date date2);
	List<User> findFirst2ByFirstName(String firstName);
	Future<List<User>> findFirst3ByFirstName(String firstName);
}
