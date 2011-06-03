package pdm.Utils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import gis.dao.City;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import pdm.beans.SearchResult;
import pdm.beans.TaxElement;

/**
 * Connector to Hibernate Database
 * 
 * @author pko
 * 
 */
public class HibernateUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -908365480173418881L;
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Session session = null;
	private static Transaction transaction;

	// private static final String APPLICATION_CONTEXT_FILE =
	// "\\WEB-INF\\spring-beans.xml";
	// private static ApplicationContext context;

	private static SessionFactory buildSessionFactory() {
		try {
			SessionFactory tmp = new Configuration().configure()
					.buildSessionFactory();
			// read();

			return tmp;

		} catch (Exception ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/*
	 * public static SessionFactory getSessionFactory() {
	 * 
	 * return sessionFactory; }
	 */

	public SessionFactory getSessionFactory() {
		return HibernateUtil.sessionFactory;
	}

	public static SessionFactory sessionFactory() {
		return HibernateUtil.sessionFactory;
	}

	public static void read() {
		Session session = HibernateUtil.sessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			// String tr = City.class.getSimpleName();
			// Query result = session.createQuery("FROM City c");
			// List<City> todos = (List) result.list();
			transaction.commit();
			// return todos;

		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
		}
	}

	public static void test2() {
		HibernateUtil.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Set<SearchResult> courses = new HashSet<SearchResult>();
			SearchResult tmp = new SearchResult();
			tmp.setTitle("tmp1");
			tmp.setDescription("description");
			courses.add(tmp);
			tmp = new SearchResult();
			tmp.setTitle("tmp2");
			tmp.setDescription("description");

			courses.add(tmp);
			TaxElement t = new TaxElement();
			t.getSearchResults().addAll(courses);

			session.save(t);

			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void test() {
	}

	@SuppressWarnings("rawtypes")
	public static List getTable(Class t) {
		List toReturn = null;
		// Session session = null;
		if (session == null)
			session = sessionFactory().openSession();

		Transaction tr = session.beginTransaction();
		try {

			Query result = session.createQuery("FROM " + t.getSimpleName()
					+ " c");
			toReturn = result.list();
			tr.rollback();

		} catch (HibernateException e) {
			e.printStackTrace();
			tr.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// session.close();
		}
		return toReturn;
	}

	public static Session getSession() {
		if (session == null || !session.isOpen())
			session = sessionFactory().openSession();
		// else session = sessionFactory().getCurrentSession();
		return session;
	}

	public static Transaction getCurrentTransaction() {
		if (transaction != null && transaction.isActive())
			return transaction;
		
		return transaction = getSession().beginTransaction();
		

	}

}
