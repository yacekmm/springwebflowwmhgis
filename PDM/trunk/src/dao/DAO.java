package dao;


import hibernate.HibernateUtil;

import java.io.Serializable;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DAO<T extends Id> implements Serializable {

	private static final long serialVersionUID = -4655693069261394927L;
	protected Vector<T> objects;

	public abstract Vector<T> getObjects(); /*{
		if (objects == null || objects.isEmpty()) {
			
//				objects = new Vector<T>(HibernateUtil.getTable(T.);

		}
		return null;
	}*/

	public void addObject(T item) {
		Session session = HibernateUtil.sessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(item);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			objects = null;
			session.close();
		}
	}
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
