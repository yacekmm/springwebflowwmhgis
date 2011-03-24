package pdm.dao;

import hibernate.HibernateUtil;

import java.util.Vector;

import pdm.beans.TaxElement;
import dao.DAO;

public class TaxElementDAO extends DAO<TaxElement> {

	private static final long serialVersionUID = -8287601353701236138L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<TaxElement> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<TaxElement>(HibernateUtil
					.getTable(TaxElement.class));

		}
		return objects;
	}
	public Vector<TaxElement> getObjectsByLevel(int level)
	{
		Vector<TaxElement> v = new Vector<TaxElement>();
		for (int i=0; i< getObjects().size();i++)
		{
		 if (getObjects().get(i).getLevel().equals(level))
			 v.add(getObjects().get(i));
			 
		}
		
	return v;
	
	}

}
