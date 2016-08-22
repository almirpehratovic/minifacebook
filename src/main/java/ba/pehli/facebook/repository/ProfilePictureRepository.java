package ba.pehli.facebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ba.pehli.facebook.domain.ProfilePicture;
import ba.pehli.facebook.domain.User;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer>{
	List<ProfilePicture> findByUser(User user);
}
