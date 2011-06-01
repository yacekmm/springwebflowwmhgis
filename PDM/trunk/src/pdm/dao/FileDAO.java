package pdm.dao;

import hibernate.HibernateUtil;

import java.util.Vector;

import pdm.beans.File;
import pdm.beans.SearchResult;
import dao.DAO;

public class FileDAO extends DAO<File> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3009568770888680055L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<File> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<File>(HibernateUtil
					.getTable(SearchResult.class));

		}
		return objects;
	}
	

	



}
