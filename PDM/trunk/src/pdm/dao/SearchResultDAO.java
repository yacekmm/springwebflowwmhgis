package pdm.dao;

import java.util.Iterator;
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

}
