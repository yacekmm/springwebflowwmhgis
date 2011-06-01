package pdm.tree;

import java.io.Serializable;

import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;
import pdm.beans.TaxElement;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;

public interface TreeBeanInterface extends Serializable {

	/**
	 * Przetwarzanie zdarzenia zaznaczenia wêz³a na drzewie taksonomii
	 * 
	 * @param event
	 *            zdarzenie zaznaczenia wêz³a
	 */
	public abstract void processSelection(NodeSelectedEvent event);

	public abstract TreeNodeImpl<TaxElement> getRootNode();

	public abstract void setRootNode(TreeNodeImpl<TaxElement> rootNode);

	public abstract void setTaxElementDAO(TaxElementDAO taxElementDAO);

	public abstract TaxElementDAO getTaxElementDAO();


	public abstract void setSearchResultDAO(SearchResultDAO searchResultDAO);

	public abstract SearchResultDAO getSearchResultDAO();

	public abstract void setSelectedNode(TaxElement selectedNode);

	public abstract TaxElement getSelectedNode();

}