package pdm.dao;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.hibernate.SQLQuery;
import org.richfaces.model.TreeNodeImpl;
import pdm.Utils.HibernateUtil;
import pdm.Utils.PdmLog;
import pdm.beans.TaxElement;

public class TaxElementDAO extends DAO<TaxElement> {

	private static final long serialVersionUID = -8287601353701236138L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<TaxElement> getObjects() {
	//	PdmLog.getLogger().info("Trying to connect to DB");
		try
		{
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<TaxElement>(HibernateUtil
					.getTable(TaxElement.class));
		}
		return objects;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PdmLog.getLogger().error("Error in Hibernate, TaxElementDAO.getObjects " + e.getStackTrace());
			return null;
		}
	}

	public Vector<TreeNodeImpl<TaxElement>> getTreeObjects() {
		Vector<TreeNodeImpl<TaxElement>> treeObjects = new Vector<TreeNodeImpl<TaxElement>>();
		for (int i = 0; i < getObjects().size(); i++) {
			TreeNodeImpl<TaxElement> tmp = new TreeNodeImpl<TaxElement>();
			getObjects().get(i).setTreeHolder(tmp);
			tmp.setData(getObjects().get(i));
			treeObjects.add(tmp);

		}

		return treeObjects;
	}

	public void reset() {
		objects = null;
	}

	public int taxonomiesCount() {

		String sql = "select count(*) from TaxElement t  where t.parentid = 0";
		SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
		BigInteger results = (BigInteger) query.uniqueResult();
		return results.intValue();
		//return results;
	}
	
	public Vector<Integer> taxonomyAll(int a)
	{
		boolean added = true;
		Vector<Integer> all = new Vector<Integer>();
		all.add(a);

		while (added) {
			added = false;
			for (int i = 0; i < all.size(); i++) {

				String sql = "select  t.id  from TaxElement t where t.parentid =  "
						+ all.get(i);
				@SuppressWarnings("unchecked")
				List<Object> tmp = HibernateUtil.getSession()
						.createSQLQuery(sql).list();
				Iterator<Object> tmpIt = tmp.iterator();
				while (tmpIt.hasNext()) {
					Integer tmpInteger = (Integer) tmpIt.next();
					if (!all.contains(tmpInteger)) {
						all.add(tmpInteger);
						added = true;
					}

				}
			}
		}
		
		
	return all;
	}

}
