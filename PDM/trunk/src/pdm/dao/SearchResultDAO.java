package pdm.dao;


import java.util.Vector;


import pdm.Utils.HibernateUtil;
import pdm.beans.SearchResult;

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
