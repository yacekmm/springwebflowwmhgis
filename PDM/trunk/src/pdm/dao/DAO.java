package pdm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Klasa wzór DAO
 * @author pkonstanczuk
 *
 * @param <T>
 */
public abstract class DAO<T extends Id> implements Serializable {
	
	protected HibernateTemplate  hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sf)
	{
		hibernateTemplate = new HibernateTemplate(sf);
		
	}
	
	

	private static final long serialVersionUID = -4655693069261394927L;
	protected List<T> objects;
/**
 * Funkcja zwraca całą zawartość tabeli DAO
 * @return
 */
	public abstract List<T> getObjects(); 
	/*{
		if (objects == null || objects.isEmpty()) {
			
//				objects = new Vector<T>(HibernateUtil.getTable(T.);

		}
		return null;
	}*/
/**
 * Funkcja zapisuje element T w bazie
 */
	public void saveOrUpdate(T item) {
		hibernateTemplate.saveOrUpdate(item);		
	}
	/**
	 * Funkcja usuwa element T z bazy
	 * @param item
	 */
	public void deleteObject(T item)
	{
		hibernateTemplate.delete(item);
	
	
	}

/**
 * Funkcja zapisuje stan w bazie
 */
	public void save()
	{
		if (objects != null)
		{
			hibernateTemplate.saveOrUpdateAll(objects);				
		}
		
	}
}
