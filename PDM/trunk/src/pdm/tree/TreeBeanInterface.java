package pdm.tree;

import java.io.Serializable;

import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;
import pdm.beans.TaxElement;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;

/**
 * Interfejs warstwy posrednia miedzy widokiem GUI, a warstwa danych (DAO)
 * 
 * @author pkonstanczuk
 */
public interface TreeBeanInterface extends Serializable {

	/**
	 * Przetwarzanie zdarzenia zaznaczenia wezla na drzewie taksonomii
	 * 
	 * @param event
	 *            zdarzenie zaznaczenia wezla
	 */
	public abstract void processSelection(NodeSelectedEvent event);

	/**
	 * Funkcja zwracajaca drzewo taksonomii, wywolywana przez GUI aplikacji
	 */
	public abstract TreeNodeImpl<TaxElement> getRootNode();

	/**
	 * Funkcja ustawiajaca drzewo taksonomii
	 */
	public abstract void setRootNode(TreeNodeImpl<TaxElement> rootNode);

	/**
	 * Setter TaxElementDAO
	 */
	public abstract void setTaxElementDAO(TaxElementDAO taxElementDAO);

	/**
	 * Getter TaxElementDAO
	 */
	public abstract TaxElementDAO getTaxElementDAO();

	/**
	 * Setter SearchResultDAO
	 */
	public abstract void setSearchResultDAO(SearchResultDAO searchResultDAO);

	/**
	 * Getter SearchResultDAO
	 */
	public abstract SearchResultDAO getSearchResultDAO();

	/**
	 * Setter SelectedNode(wybranej galezi drzewa taksonomii - obsluga GUI)
	 */
	public abstract void setSelectedNode(TaxElement selectedNode);

	/**
	 * Getter SelectedNode(wybranej galezi drzewa taksonomii - obsluga GUI)
	 */
	public abstract TaxElement getSelectedNode();

}