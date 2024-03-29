package pdm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pdm.dao.Id;
/**
 * Klasa mapujaca instacje wyniku wyszukiwania na baze danych
 * @author pkonstanczuk
 */
public class SearchResult implements Serializable, Id {
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = -6182164701336160757L;
	/**
	 * Id
	 */
	private Integer id;
	/**
	 * tytul i opis instancji
	 */
	private String title, description;
	/** 
	 * Zbior plikow(obrazow) przypisany do obiektu
	 */
	private Set<File> files = new HashSet<File>(0);
	/**
	 * 
	 */
	private Set<TaxElement> taxElements = new HashSet<TaxElement>(0);

	/**
	 * Domyslny konstruktor
	 */
	public SearchResult() {
	}
	/**
	 * Konstruktor parametryzowany
	 * @param _id
	 * @param _title
	 * @param _desc
	 */

	public SearchResult(int _id, String _title, String _desc) {
		id = _id;
		title = _title;
		description = _desc;

	}
/**
 * Setter id
 */
	public void setId(Integer id) {
		this.id = id;
	}
/**
 * Getter id
 */
	public Integer getId() {
		return id;
	}
	/**
	 * Setter title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Getter title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Setter description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Getter description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Setter files(typ wejsciowy - Set)
	 */
	public void setFiles(Set<File> files) {
		this.files = files;
	}
	/**
	 * Setter files(typ wejsciowy - ArrayList)
	 */
	public void setFiles(ArrayList<File> files) {
		getFiles().clear();
		files.addAll(files);
	}
	/**
	 * Getter files(typ wejsciowy - Set)
	 */
	public Set<File> getFiles() {
		if (files == null)
			files = new HashSet<File>();

		return files;
	}
	/**
	 * Getter pierwszego pliku, jesli jest
	 */
	public File getFile() {
		
		
		if (getFiles().iterator().hasNext())
		{
			return getFiles().iterator().next();
			
			
		}
		
			return null;
	}
	public void setTaxElements(Set<TaxElement> taxElements) {
		this.taxElements = taxElements;
	}
	public Set<TaxElement> getTaxElements() {
		return taxElements;
	}
	
	
	public String getTaxonomiesDescription()
	{
		
		StringBuffer bf = new StringBuffer();
		bf = bf.append("Obiekt sklasyfikowano: <br/>");
		for ( TaxElement t : getTaxElements() )
		{
			bf.append("- ");
			bf.append(t.getTraceLazy());
			bf.append("<br/>");
		}
		return bf.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
