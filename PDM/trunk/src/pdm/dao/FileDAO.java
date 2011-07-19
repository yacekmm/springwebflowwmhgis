package pdm.dao;


import java.util.Vector;

import pdm.Utils.HibernateUtil;
import pdm.beans.File;
import pdm.beans.SearchResult;

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
	
	public File getFileById(int id)
	{
		File obj = (File)HibernateUtil.getSession().load(File.class, id);
		return obj;
	}
	

	



}
