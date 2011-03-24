package org.yacekm.Utils;

public class SearchResult {
	private int id;
	private String title, description, imagePath;
	
	public SearchResult(){}
	
	public SearchResult(int _id, String _title, String _desc, String _imagePath){
		id= _id;
		title=_title;
		description=_desc;
		imagePath=_imagePath;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
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
		return description;
	}
}
