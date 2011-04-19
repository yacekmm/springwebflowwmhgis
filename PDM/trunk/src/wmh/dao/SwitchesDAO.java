package wmh.dao;

import hibernate.HibernateUtil;

import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import wmh.beans.*;

/**
 * DAO for switches
 * 
 * @author pko
 * 
 */
public class SwitchesDAO {

	private Vector<Switch> switches;

	/**
	 * sets table of switches
	 * 
	 * @param switches
	 */
	public void setSwitches(Vector<Switch> switches) {
		this.switches = switches;
	}

	/**
	 * returns table of switches
	 * 
	 * @return
	 */
	public Vector<Switch> getSwitches() {
		if (switches == null)
			switches = new Vector<Switch>();
		return switches;
	}

	/**
	 * clears the table
	 */
	public void clear() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("delete from Switch");
		q.executeUpdate();
		session.getTransaction().commit();
		getSwitches().clear();
	}

	/**
	 * returns amount of the elements in the table
	 * 
	 * @return
	 */
	public int size() {
		return getSwitches().size();
	}

	/**
	 * saves the table to the database
	 * 
	 * @return
	 */
	public boolean save() {

		org.hibernate.Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			for (int ix = 0; ix < getSwitches().size(); ix++)
				s.save(getSwitches().get(ix));
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
