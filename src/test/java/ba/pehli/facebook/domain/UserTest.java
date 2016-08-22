package ba.pehli.facebook.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Test;

public class UserTest {

	@Test
	public void testPictures() throws IOException, URISyntaxException {
		
		
		ProfilePicture picture1 = new ProfilePicture(1,Files.readAllBytes(Paths.get(UserTest.class.getResource("user-user.jpeg").toURI())));
		ProfilePicture picture2 = new ProfilePicture(1,Files.readAllBytes(Paths.get(UserTest.class.getResource("user-user-2.jpeg").toURI())));
		
		User user = new User(1, "test", "pass", "John", "Doe", new Date(), "M");
		user.addPicture(picture1);
		user.addPicture(picture2);
		
		assertEquals(user.getPictures().size(), 2);
	}

}
