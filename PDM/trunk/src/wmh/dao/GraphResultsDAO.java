package wmh.dao;

//import hibernate.HibernateUtil;

import java.io.Serializable;
//import java.util.List;
import java.util.Vector;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.Transaction;

import wmh.beans.*;
/**
 * Holds results for WMH project
 * @author pko
 *
 */
public class GraphResultsDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3646111569123276649L;
	private Vector<GraphResults> graphResults;


/**
 * 
 * @return list of graph results
 */

	public Vector<GraphResults> getGraphResults() {
	/*	Session session = HibernateUtil.getSession();
		// Transaction tr = session.beginTransaction();
		Query result = session.createQuery("FROM GraphResults gr");
		List r = result.list();

		graphResults = new Vector<GraphResults>(r); */
		if (graphResults == null)
			graphResults = new Vector<GraphResults>();

		return graphResults;
	}


/**
 * clears table
 */
	public void clear() {
		/*Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("delete from GraphResults");
		q.executeUpdate();
		session.getTransaction().commit();*/
		getGraphResults().clear();
	}
/**
 * 
 * @return size of results
 */
	public int size() {
		return getGraphResults().size();
	}
/**
 * Saves table to the database
 * @return
 */
	public boolean save() {

	/*	org.hibernate.Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			for (java.util.Iterator<GraphResults> i = graphResults
					.iterator(); i.hasNext();)
			{
			//	GraphResults tmp = i.next();
				s.save(i.next());
			}
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

		}*/
		return true;

	}
	
	/*@SuppressWarnings("unchecked")
	public Vector<GraphResults> averageIntegrity(int vertexNumber)
	{
		Session session = HibernateUtil.getSession();
		String sql_query = "select p.errorRate as WspolczynnikBledu,count(*) as IloscProb, avg(p.integrityParamter), sum(p.integrityCount) as SpojnoscGrafu from GraphResults p where p.vertexNumber = " + vertexNumber + " group by p.errorRate order by p.errorRate";
		      Query query = session.createQuery(sql_query);
		      //List res = query.list();
		      Vector<GraphResults> graphAvg = new Vector<GraphResults>();
		      GraphResults tmp = null;
		      for(Iterator it= query.iterate();it.hasNext();){
		        Object[] row = (Object[]) it.next();
		        tmp = new GraphResults();
		        tmp.setErrorRate((Integer) row[0]);
		        tmp.setTrials((Long)row[1]);
		        tmp.setVertexNumber(vertexNumber);
		        tmp.setIntegrityCount(((Long)row[3]).intValue());
		        tmp.setIntegrityParamter(((Double) row[2]).intValue());
		        graphAvg.add(tmp);
		      }		      
		      return graphAvg;

	}*/
}
