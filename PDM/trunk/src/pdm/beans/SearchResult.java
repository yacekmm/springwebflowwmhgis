package pdm.beans;

import java.io.Serializable;

import dao.Id;

public class SearchResult implements Serializable, Id{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6182164701336160757L;
	private int id;
	private String title, description, imagePath;
	
	public SearchResult(){}
	
	public SearchResult(int _id, String _title, String _desc, String _imagePath){
		id= _id;
		title=_title;
		description=_desc;
		imagePath=_imagePath;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		if (description == null)
		return	"To jest miejsce na opis";
		return description;
	}
}
