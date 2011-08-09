package pdm.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	 * Funkcja zwraca całą zawartość tabeli DAO
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

	/**
	 * Zwraca wszystkie searchResult pasujące do instancji taksonomii o podanych
	 * Id
	 * 
	 * @param taxIds
	 * @return
	 * 
	 *         public Vector<SearchResult> findAllMatching(Vector<Integer>
	 *         taxIds) { Vector<SearchResult> result = new
	 *         Vector<SearchResult>(); for (int i = 0; i < taxIds.size(); i++) {
	 *         result.addAll(findMatchingForOne(taxIds.get(i))); } return
	 *         result; } /* /** Zwraca wszystkie searchResult pasujące do
	 *         instancji taksonomii o podanym Id
	 * 
	 * @param taxId
	 * @return
	 * 
	 *         public Vector<SearchResult> findMatchingForOne(Integer taxId) {
	 *         Vector<SearchResult> result = new Vector<SearchResult>();
	 *         Vector<Integer> searchResultIds = new Vector<Integer>(); String
	 *         sql =
	 *         "select  searchresult_id from TAXELEMENT_SEARCHRESULT sr where sr.taxelement_id =  "
	 *         + taxId;
	 * @SuppressWarnings("unchecked") List<Integer> tmp =
	 *                                HibernateUtil.getSession
	 *                                ().createSQLQuery(sql) .list();
	 *                                searchResultIds.addAll(tmp);
	 * 
	 *                                for (int i2 = 0; i2 <
	 *                                searchResultIds.size(); i2++) {
	 * 
	 *                                sql =
	 *                                "select  *  from SEARCHRESULT sr where sr.id =  "
	 *                                + searchResultIds.get(i2);
	 * @SuppressWarnings("unchecked") List<SearchResult> tmp2 =
	 *                                HibernateUtil.getSession()
	 *                                .createSQLQuery(
	 *                                sql).addEntity(SearchResult.class).list();
	 *                                result.addAll(tmp2); }
	 * 
	 *                                return result;
	 * 
	 *                                }
	 */
	
	public ArrayList<SearchResult> check(Collection<TaxElement> set)
	{
	 if (set.isEmpty())
		 return new ArrayList<SearchResult>();
		
		String hql = "select a from SearchResult a " +
                "join a.taxElements t " +
                "where t in (:elem)";
		Session s = hibernateTemplate.getSessionFactory().openSession();
		Query query = s.createQuery(hql);
		query.setParameterList("elem", set);
		query.setFirstResult(18);
		query.setMaxResults(18);
		
		@SuppressWarnings("unchecked")
		ArrayList<SearchResult> l = (ArrayList<SearchResult>) query.list();
		s.close();
		return l;
		
	
	}

}
