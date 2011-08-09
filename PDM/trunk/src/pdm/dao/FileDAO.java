package pdm.dao;


import java.util.List;
import pdm.beans.File;

/**
 * DAO dla plików
 * @author pkonstanczuk
 *
 */
public class FileDAO extends DAO<File> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3009568770888680055L;
/**
 * Funkcja zwraca wszystkie pliki z bazy
 */
	
	@Override
	public List<File> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = hibernateTemplate.loadAll(File.class );
			
		}
		return objects;
	}
	/**
	 * Funkcja zwraca plik z bazy po jego Id
	 * @param id
	 * @return
	 */
	public File  load(Integer id)
	{
		return hibernateTemplate.load(File.class, id);		
	}
	

	



}
