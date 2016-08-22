package ba.pehli.facebook.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.annotation.Order;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="users")
public class User implements Serializable{
	private static final Logger logger = Logger.getLogger(User.class);
	
	private Integer id;
	private String username;
	private String role = "ROLE_USER";
	private String password;
	private String firstName;
	private String lastName;
	private boolean enabled = true;
	private Date birthDate;
	private String gender;
	private String country;
	
	private List<ProfilePicture> pictures = new ArrayList<ProfilePicture>();
	private List<Post> posts = new ArrayList<Post>();
	
	public User(){
		logger.debug("##### > new User()");
	}
	
	public User(Integer id, String username, String password, String firstName,
			String lastName, Date birthDate, String gender) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		logger.debug("##### > user.setId(" + id + ")");
		this.id = id;
	}
	
	@NotEmpty(message="Username cannot be empty")
	@Size(min=3,max=60,message="Username must be between 3 and 60 chars long")
	@Column(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotEmpty(message="Password cannot be empty")
	@Size(min=3,max=60,message="Password must be between 3 and 60 chars long")
	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotEmpty(message="First Name cannot be empty")
	@Size(min=2,message="Minimal size is 2")
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotEmpty(message="Last Name cannot be empty")
	@Size(min=2,message="Minimal size is 2")
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@DateTimeFormat(pattern="dd.MM.yyyy")
	@Column(name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@NotEmpty(message="Gender cannot be empty")
	@Column(name="gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@NotNull(message="Field enabled must be set")
	@Column(name="enabled")	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@NotEmpty(message="User role cannot be empty")
	@Column(name="role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@OrderBy("id DESC")
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true)
	public List<ProfilePicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<ProfilePicture> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(ProfilePicture picture){
		pictures.add(picture);
	}
	
	@OrderBy("id DESC")
	@OneToMany(mappedBy="poster", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonIgnore
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost(Post post){
		posts.add(post);
	}
	
	@Column(name="country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", role=" + role
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", enabled=" + enabled + ", birthDate=" + birthDate + "]";
	}
	
	
	
}
