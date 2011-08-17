package pdm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pdm.dao.Id;
/**
 * Klasa mapująca instację wyniku wyszukiwania na bazę danych
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
	 * tytuł i opis instancji
	 */
	private String title, description;
	/** 
	 * Zbiór plików(obrazów) przypisany do obiektu
	 */
	private Set<File> files = new HashSet<File>(0);
	/**
	 * 
	 */
	private Set<TaxElement> taxElements = new HashSet<TaxElement>(0);

	/**
	 * Domyślny konstruktor
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
	 * Setter files(typ wejściowy - Set)
	 */
	public void setFiles(Set<File> files) {
		this.files = files;
	}
	/**
	 * Setter files(typ wejściowy - ArrayList)
	 */
	public void setFiles(ArrayList<File> files) {
		getFiles().clear();
		files.addAll(files);
	}
	/**
	 * Getter files(typ wejściowy - Set)
	 */
	public Set<File> getFiles() {
		if (files == null)
			files = new HashSet<File>();

		return files;
	}
	/**
	 * Getter pierwszego pliku, jeśli jest
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
			bf.append(t.toString());
			bf.append("<br/>");
		}
		return bf.toString();
	}
	
}
