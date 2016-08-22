package ba.pehli.facebook.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="profile_pictures")
public class ProfilePicture implements Serializable{
	
	private Integer id;
	private byte[] image;
	private User user;
	
	public ProfilePicture(){}

	
	public ProfilePicture(Integer id, byte[] image) {
		this.id = id;
		this.image = image;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_pictures")
	@SequenceGenerator(name="seq_pictures", sequenceName="seq_pictures", allocationSize=1)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Lob
	@Basic(fetch=FetchType.EAGER)
	@NotNull(message="Image cannot be null")
	@Column(name="image")
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
}
