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
	

}
