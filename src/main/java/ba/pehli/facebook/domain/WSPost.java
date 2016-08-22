package ba.pehli.facebook.domain;

import java.util.Date;

/**
 * Objekat koji se šalje Web Socket protokolom klijentu
 * @author almir
 *
 */
public class WSPost {
	private int id;
	private int posterId;
	private String posterName;
	private int posterPictureId;
	private Date date;
	private String text;
	
	public WSPost(){}
	
	public WSPost(int id,int posterId,String posterName, int posterPictureId, Date date,
			String text) {
		this.id = id;
		this.posterId = posterId;
		this.posterName = posterName;
		this.posterPictureId = posterPictureId;
		this.date = date;
		this.text = text;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosterId() {
		return posterId;
	}
	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
	public String getPosterName() {
		return posterName;
	}
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	public int getPosterPictureId() {
		return posterPictureId;
	}
	public void setPosterPictureId(int posterPictureId) {
		this.posterPictureId = posterPictureId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
