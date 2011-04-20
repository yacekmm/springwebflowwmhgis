package pdm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;

import pdm.beans.TaxElement;
import pdm.dao.ResultsIndexDAO;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;

public class TreeBean implements Serializable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeNodeImpl<TaxElement> rootNode = null;
	private TaxElementDAO taxElementDAO;
	private ResultsIndexDAO resultsIndexDAO;
	private SearchResultDAO searchResultDAO;
	
	private TaxElement selectedNode = null;
	private List<TaxElement> selectedConcept;
	private List<List<TaxElement>> selectedConceptHistory;
	private int conceptHistorySize = 8;
	
	private String testValue;
	
    /*private List<TreeNode<String>> selectedNodeChildren = new ArrayList<TreeNode<String>>();
	

	public List<TreeNode<String>> getSelectedNodeChildren() {
		return selectedNodeChildren;
	}

	public void setSelectedNodeChildren(List<TreeNode<String>> selectedNodeChildren) {
		this.selectedNodeChildren = selectedNodeChildren;
	}*/

	public TreeBean(){
		selectedConcept = new ArrayList<TaxElement>();
		selectedConceptHistory = new ArrayList<List<TaxElement>>();
	}
	
	public TreeNodeImpl<TaxElement> getRootNode() {
		if (rootNode == null)
			loadTree();
		return rootNode;
	}

	public void setRootNode(TreeNodeImpl<TaxElement> rootNode) {
		this.rootNode = rootNode;
	}

	//private List<String> selectedNodeChildren = new ArrayList<String>();

	private String nodeTitle;

	public String getNodeTitle() {
		return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	private void loadTree() {
		Vector<TreeNodeImpl<TaxElement>> elements = taxElementDAO.getTreeObjects();
		
		
		for (int i = 0; i < elements.size();i++)
		{
			for (int i2 = 0;i2 < elements.size(); i2++)
			if (elements.get(i).getData().getParentId() == elements.get(i2).getData().getId())
				elements.get(i2).addChild(elements.get(i).getData().getId(),elements.get(i) );
		}
		
		rootNode = new TreeNodeImpl<TaxElement>();
		
		
		for (int i = 0; i < elements.size();i++)
		{
			if (elements.get(i).getData().getParentId() ==0)
				rootNode.addChild(elements.get(i).getData().getId(),elements.get(i) );
		}
	}

	/**
	 * Przetwarzanie zdarzenia zaznaczenia wêz³a na drzewie taksonomii
	 * @param event zdarzenie zaznaczenia wêz³a
	 */
	public void processSelection(NodeSelectedEvent event) {
		// odczytaj wybrany wezel
		HtmlTree tree = (HtmlTree) event.getComponent();
		selectedNode = (TaxElement)tree.getRowData();
		selectedConcept = new ArrayList<TaxElement>();
		
		//wype³niaj wybrany koncept elementami taksonomii az do rodzica
		do{
			selectedConcept.add(0, selectedNode);
			selectedNode = selectedNode.getTreeHolder().getParent().getData();
		}while(selectedNode != null);
		
		//dodaj wezel do historii konceptów
		selectedConceptHistory.add(0, selectedConcept);
		if(selectedConcept.size()> conceptHistorySize)
			selectedConcept.remove(conceptHistorySize);
	}
	
	public void dropListener(DropEvent dropEvent)
	{
	/*	//destination attributtes
		UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getSource() : null;
		UITree destTree = destNode != null ? destNode.getUITree() : null;
	    TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDropValue() : null;
	    TreeNode droppedInNode = dropNodeKey != null ? destTree.getTreeNode(dropNodeKey) : null;
	    //drag source attributes
	    UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getDraggableSource() : null;
	    UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
	    TreeRowKey dragNodeKey = (dropEvent.getDragValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDragValue() : null;
	    TreeNode draggedNode = dragNodeKey != null ? srcTree.getTreeNode(dragNodeKey) : null;
	    */
	}

	public void setTaxElementDAO(TaxElementDAO taxElementDAO) {
		this.taxElementDAO = taxElementDAO;
	}

	public TaxElementDAO getTaxElementDAO() {
		return taxElementDAO;
	}

	public void setResultsIndexDAO(ResultsIndexDAO resultsIndexDAO) {
		this.resultsIndexDAO = resultsIndexDAO;
	}

	public ResultsIndexDAO getResultsIndexDAO() {
		return resultsIndexDAO;
	}

	public void setSearchResultDAO(SearchResultDAO searchResultDAO) {
		this.searchResultDAO = searchResultDAO;
	}

	public SearchResultDAO getSearchResultDAO() {
		return searchResultDAO;
	}

	public void setSelectedNode(TaxElement selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TaxElement getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedConcept(List<TaxElement> selectedConcept) {
		this.selectedConcept = selectedConcept;
	}

	public List<TaxElement> getSelectedConcept() {
		return selectedConcept;
	}

	public void setSelectedConceptHistory(List<List<TaxElement>> selectedConceptHistory) {
		this.selectedConceptHistory = selectedConceptHistory;
	}

	public List<List<TaxElement>> getSelectedConceptHistory() {
		return selectedConceptHistory;
	}

	public void setConceptHistorySize(int conceptHistorySize) {
		this.conceptHistorySize = conceptHistorySize;
	}

	public int getConceptHistorySize() {
		return conceptHistorySize;
	}

	public void setTestValue(String testValue) {
		for (TaxElement te : selectedConcept) {
			te.setFace("standard");
		}
		this.testValue = testValue;
	}

	public String getTestValue() {
		return testValue;
	}

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		for (TaxElement te : selectedConcept) {
			te.setFace("standard");
		}
	}
}