package pdm.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pdm.dao.Id;


public class SearchResult implements Serializable, Id{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6182164701336160757L;
	private Integer id;
	private String title, description;
	private Set<File> files = new HashSet<File>(0);
	public SearchResult(){}
	
	public SearchResult(int _id, String _title, String _desc){
		id= _id;
		title=_title;
		description=_desc;
		
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

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<File> getFiles() {
		return files;
	}
}
