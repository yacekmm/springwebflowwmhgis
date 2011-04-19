package pdm.dao;

import hibernate.HibernateUtil;

import java.util.Vector;


import pdm.beans.SearchResult;
import dao.DAO;

public class SearchResultDAO extends DAO<SearchResult> {

	private static final long serialVersionUID = -5777728131987585529L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<SearchResult> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<SearchResult>(HibernateUtil
					.getTable(SearchResult.class));

		}
		return objects;
	}

}
