package ba.pehli.facebook.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="posts")
public class Post implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="gen")
	@TableGenerator(name="gen",table="obj_ids",pkColumnName="obj_type",pkColumnValue="Post",valueColumnName="obj_next_id",allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	@NotEmpty(message="Text cannot be empty")
	@Column(name="text")
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datetime")	
	private Date dateTime;
	
	@NotNull(message="Poster cannot be null")
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User poster;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="likes", joinColumns=@JoinColumn(name="post_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> usersLikingThis = new ArrayList<User>();
	
	public Post(){}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	public User getPoster() {
		return poster;
	}

	public void setPoster(User user) {
		this.poster = user;
	}
	

	public List<User> getUsersLikingThis() {
		return usersLikingThis;
	}

	public void setUsersLikingThis(List<User> usersLikingThis) {
		this.usersLikingThis = usersLikingThis;
	}
	
	public void addUserLikingThis(User user){
		usersLikingThis.add(user);
	}
	
}
