package dao;


import hibernate.HibernateUtil;

import java.io.Serializable;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAO<T>  implements Serializable{
	

	private static final long serialVersionUID = -4655693069261394927L;
	private Vector<T> objects;
	
	
	public Vector<T> getObjects()
	{
		if (objects == null)
		{
			
		}
		return null;
	}
	public void addObject(T item)
	{
	Session session = HibernateUtil.sessionFactory().openSession();
	Transaction transaction = null;

	try {
		transaction = session.beginTransaction();
		Object tmp = session.save(item);
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
