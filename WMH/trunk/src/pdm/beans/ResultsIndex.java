package pdm.beans;

import java.io.Serializable;

import dao.Id;
//holds id of search result and representation in string where in taxonomy it is
public class ResultsIndex implements Serializable, Id {

	private static final long serialVersionUID = -4589391716697650069L;
	private int id, searchResultId;
	private String taxonomyIndex;

	@Override
	public Integer getId() {
		
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id =id;

	}

	public void setSearchResultId(int searchResultId) {
		this.searchResultId = searchResultId;
	}

	public int getSearchResultId() {
		return searchResultId;
	}

	public void setTaxonomyIndex(String taxonomyIndex) {
		this.taxonomyIndex = taxonomyIndex;
	}

	public String getTaxonomyIndex() {
		return taxonomyIndex;
	}

}
