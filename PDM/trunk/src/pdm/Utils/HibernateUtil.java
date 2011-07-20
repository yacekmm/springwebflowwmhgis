package pdm.Utils;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;


/**
 * Connector to Hibernate Database, szczegóły w dokumentacji Hibernate
 * 
 * @author pko
 * 
 */
public class HibernateUtil implements Serializable {

	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = -908365480173418881L;
	/**
	 * Aktualne sessionFactory
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();
	/**
	 * Aktualnie otwarta sesja
	 */	
	private static Session session = null;
	/**
	 * Aktualnie rozpoczęta transakcja
	 */
	private static Transaction transaction;


/**
 * Funkcja budująca SessionFactory
 * @return
 */
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

/**
 * Getter SessionFactory
 * @return
 */
	public SessionFactory getSessionFactory() {
		return HibernateUtil.sessionFactory;
	}
/**
 * Setter SessionFactory
 * @return
 */
	public static SessionFactory sessionFactory() {
		return HibernateUtil.sessionFactory;
	}	

/**
 * Funkcja zwracająca tablice danego typu zmapowanego do bazy danych
 * @param t
 * @return
 */
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
/**
 * Getter session
 * @return
 */
	public static Session getSession() {
		if (session == null || !session.isOpen())
			session = sessionFactory().openSession();
		// else session = sessionFactory().getCurrentSession();
		return session;
	}
/**
 * Getter currentTransaction
 * @return
 */
	public static Transaction getCurrentTransaction() {
		if (transaction != null && transaction.isActive())
			return transaction;
		
		return transaction = getSession().beginTransaction();
		

	}

}
