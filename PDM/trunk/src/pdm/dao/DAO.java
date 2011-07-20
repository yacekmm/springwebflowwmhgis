package pdm.dao;

import java.io.Serializable;
import java.util.Vector;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pdm.Utils.HibernateUtil;
/**
 * Klasa wzór DAO
 * @author pkonstanczuk
 *
 * @param <T>
 */
public abstract class DAO<T extends Id> implements Serializable {

	private static final long serialVersionUID = -4655693069261394927L;
	protected Vector<T> objects;
/**
 * Funkcja zwraca całą zawartość tabeli DAO
 * @return
 */
	public abstract Vector<T> getObjects(); /*{
		if (objects == null || objects.isEmpty()) {
			
//				objects = new Vector<T>(HibernateUtil.getTable(T.);

		}
		return null;
	}*/
/**
 * Funkcja zapisuje element T w bazie
 */
	public void saveOrUpdate(T item) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(item);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			objects = null;
			//session.close();
		}
	}
	/**
	 * Funkcja usuwa element T z bazy
	 * @param item
	 */
	public void deleteObject(T item)
	{
		Session session = HibernateUtil.sessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.delete(item);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			objects = null;
			session.close();
		}
	}

/**
 * Funkcja zapisuje stan w bazie
 */
	public void save()
	{
		if (objects != null)
		{
			Session session = HibernateUtil.sessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				for (int i=0; i< objects.size();i++)
					session.save(objects.get(i));
				
				transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				e.printStackTrace();
			} finally {
				objects = null;
				session.close();
			}

		
		}
		
	}
}
