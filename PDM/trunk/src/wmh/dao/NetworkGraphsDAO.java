package wmh.dao;

import hibernate.HibernateUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import wmh.beans.*;
/**
 * DAO for networkGraphs
 * @author pko
 *
 */
public class NetworkGraphsDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3548534481621214433L;
	private Vector<NetworkGraph> networkGraphs;


/**
 * clears table
 */
	public void clear() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("delete from NetworkGraph");
		q.executeUpdate();
		session.getTransaction().commit();
		getNetworkGraphs().clear();
	}
/**
 * returns amount of elements int the table
 * @return
 */
	public int size() {
		return getNetworkGraphs().size();
	}
/**
 * saves the table to database
 * @return
 */
	public boolean save() {

		org.hibernate.Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			for (java.util.Iterator<NetworkGraph> i = networkGraphs.iterator(); i
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

	public void setNetworkGraphs(Vector<NetworkGraph> networkGraphs) {
		this.networkGraphs = networkGraphs;
	}

	@SuppressWarnings("unchecked")
	public Vector<NetworkGraph> getNetworkGraphs() {
		Session session = HibernateUtil.getSession();
		Query result = session.createQuery("FROM NetworkGraph ng");
		@SuppressWarnings("rawtypes")
		List r = result.list();
		networkGraphs = new Vector<NetworkGraph>(r);
		return networkGraphs;
	}
}
