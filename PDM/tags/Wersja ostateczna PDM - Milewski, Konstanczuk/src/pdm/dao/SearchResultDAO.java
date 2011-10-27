package pdm.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import pdm.Utils.Const;
import pdm.Utils.Validator;
import pdm.beans.SearchResult;
import pdm.beans.TaxElement;

/**
 * DAO dla SearchResult
 * 
 * @author pkonstanczuk
 * 
 */
public class SearchResultDAO extends DAO<SearchResult> {

	private static final long serialVersionUID = -5777728131987585529L;
	/**
	 * Referencja do FileDAO
	 */
	private FileDAO fileDAO;

	/**
	 * Funkcja zwraca cala zawartosc tabeli DAO
	 * 
	 * @return
	 */

	@Override
	public List<SearchResult> getObjects() {

		if (objects == null || objects.isEmpty()) {
			objects = (Vector<SearchResult>) hibernateTemplate
					.loadAll(SearchResult.class);

		}

		return objects;
	}

	/**
	 * Funkcja zapisuje element item w bazie
	 */
	@Override
	public void saveOrUpdate(SearchResult item) {

		if (item.getFiles() != null) {
			for (Iterator<pdm.beans.File> it = item.getFiles().iterator(); it
					.hasNext();) {
				fileDAO.saveOrUpdate(it.next());
			}
		}
		super.saveOrUpdate(item);
		Validator.setInfoMessage(Const.success);

	};

	/**
	 * Setter fileDAO
	 * 
	 * @param fileDAO
	 */
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	/**
	 * Getter FileDAO
	 * 
	 * @return
	 */
	public FileDAO getFileDAO() {
		return fileDAO;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<SearchResult> loadList(Collection<Integer> ids) {

		ArrayList<SearchResult> result;

		if (ids.isEmpty())
			return new ArrayList<SearchResult>();
		String hql = "select a from SearchResult a " + "where a.id in (:elem)";
		Session s = hibernateTemplate.getSessionFactory().openSession();
		Query query = s.createQuery(hql);
		query.setParameterList("elem", ids);
	//	query.setFirstResult(18);
	//	query.setMaxResults(18);
		result = (ArrayList<SearchResult>) query.list();
		s.close();

		return result;
	}

	public ArrayList<SearchResult> check(Collection<TaxElement> set) {
		// Test(set);
		if (set.isEmpty())
			return new ArrayList<SearchResult>();

		String hql = "select a from SearchResult a " + "join a.taxElements t "
				+ "where t in (:elem)";
		Session s = hibernateTemplate.getSessionFactory().openSession();
		Query query = s.createQuery(hql);
		query.setParameterList("elem", set);
		
		//query.setFirstResult(18);
		//query.setMaxResults(18);

		@SuppressWarnings("unchecked")
		ArrayList<SearchResult> l = (ArrayList<SearchResult>) query.list();
		s.close();
		return l;

	}

	public ArrayList<Integer> check2(Collection<TaxElement> set) {
		if (set.isEmpty())
			return new ArrayList<Integer>();

		StringBuilder sql = new StringBuilder(
				"select  sr.searchresult_id from TAXELEMENT_SEARCHRESULT sr where sr.taxelement_id in  (");

		for (TaxElement t : set) {
			sql.append(t.getId().toString() + ",");

		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");

		Session s = hibernateTemplate.getSessionFactory().openSession();
		Query query = s.createSQLQuery(sql.toString());

		@SuppressWarnings("unchecked")
		ArrayList<Integer> l = (ArrayList<Integer>) query.list();
		s.close();
		return l;

	}

}
