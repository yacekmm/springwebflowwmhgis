package wmh.dao;

import hibernate.HibernateUtil;
import java.util.Vector;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import wmh.beans.*;

/**
 * holds connections for WMH project
 * @author pko
 *
 */
public class ConnectionsDAO {

	private Vector<Connection> connections;

	public void setConnections(Vector<Connection> connections) {
		this.connections = connections;
	}
/**
 * 
 * @return Connection list
 */
	public Vector<Connection> getConnections() {
		if (connections == null)
			connections = new Vector<Connection>();
		return connections;
	}



/**
 * Clears connection list
 */
	public void clear() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("delete from Connection");
		q.executeUpdate();
		session.getTransaction().commit();
		getConnections().clear();
	}
/**
 * 
 * @return amount of connections
 */
	public int size() {
		return getConnections().size();
	}


/**
 * Saves connections to the database
 * @return status of process
 */
	public boolean save() {

		org.hibernate.Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			for (java.util.Iterator<Connection> i = getConnections().iterator(); i
					.hasNext();)
				s.save(i.next());
			tr.commit();
			return true;
		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			e.printStackTrace();
			return false;
		} finally {
			if (s != null)
				s.close();

		}

	}

}
