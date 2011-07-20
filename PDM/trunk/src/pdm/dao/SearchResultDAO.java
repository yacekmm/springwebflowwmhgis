package pdm.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import pdm.Utils.Const;
import pdm.Utils.HibernateUtil;
import pdm.Utils.Validator;
import pdm.beans.SearchResult;

public class SearchResultDAO extends DAO<SearchResult> {

	private static final long serialVersionUID = -5777728131987585529L;

	private FileDAO fileDAO;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<SearchResult> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<SearchResult>(
					HibernateUtil.getTable(SearchResult.class));

		}
		return objects;
	}

	@Override
	public void saveOrUpdate(SearchResult item) {

		
		if (item.getFiles() != null)
		{
			for (Iterator<pdm.beans.File> it = item.getFiles().iterator(); it.hasNext(); )
			{
				fileDAO.saveOrUpdate(it.next());				
			}
		}
		super.saveOrUpdate(item);
		Validator.setInfoMessage(Const.success);
			
	};

	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	public FileDAO getFileDAO() {
		return fileDAO;
	}
	
	public Vector<SearchResult> findAllMatching(Vector<Integer> taxIds)
	{
		Vector<SearchResult> result = new Vector<SearchResult>();
		Vector<Integer> searchResultIds = new Vector<Integer>();
		for (int i = 0; i < taxIds.size(); i++) {

			String sql = "select  searchresult_id from taxelement_SearchResult sr where sr.taxelement_id =  "
					+ taxIds.get(i);
			@SuppressWarnings("unchecked")
			List<Integer> tmp = HibernateUtil.getSession()
			.createSQLQuery(sql).list();
			searchResultIds.addAll(tmp);
		}
		
		for (int i2 = 0; i2 < searchResultIds.size(); i2++) {

			String sql = "select  *  from SearchResult sr where sr.id =  "
					+ searchResultIds.get(i2);
			@SuppressWarnings("unchecked")
			List<SearchResult> tmp2 = HibernateUtil.getSession()
			.createSQLQuery(sql).addEntity(SearchResult.class).list();
			result.addAll(tmp2);
		}
		
		return result; 
	}

}
