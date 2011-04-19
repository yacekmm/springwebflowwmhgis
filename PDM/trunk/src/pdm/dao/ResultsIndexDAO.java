package pdm.dao;

import hibernate.HibernateUtil;

import java.util.Vector;

import pdm.beans.ResultsIndex;
import dao.DAO;

public class ResultsIndexDAO extends DAO<ResultsIndex> {
private static final long serialVersionUID = 5817701547330811265L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<ResultsIndex> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<ResultsIndex>(HibernateUtil
					.getTable(ResultsIndex.class));

		}
		return objects;
	}
}
