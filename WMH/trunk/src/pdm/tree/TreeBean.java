package pdm.tree;



import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;

import pdm.beans.TaxElement;
import pdm.dao.ResultsIndexDAO;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;

public class TreeBean {
	private TreeNodeImpl<TaxElement> rootNode = null;
	private TaxElementDAO taxElementDAO;
	private ResultsIndexDAO resultsIndexDAO;
	private SearchResultDAO searchResultDAO;
	
	private String selectedNode = null;
	private List<String> selectionHistory;
	
	private List<SelectItem> selectedNodeTokenized;
	private List<List<SelectItem>> selectionHistoryList;
	
	private List<SelectItem> testList;
	
    /*private List<TreeNode<String>> selectedNodeChildren = new ArrayList<TreeNode<String>>();
	

	public List<TreeNode<String>> getSelectedNodeChildren() {
		return selectedNodeChildren;
	}

	public void setSelectedNodeChildren(List<TreeNode<String>> selectedNodeChildren) {
		this.selectedNodeChildren = selectedNodeChildren;
	}*/

	public TreeBean(){
		selectionHistory = new ArrayList<String>();
		selectedNodeTokenized = new ArrayList<SelectItem>();
		selectionHistoryList = new ArrayList<List<SelectItem>>();
		
		testList = new ArrayList<SelectItem>();
		
		testList.add(new SelectItem("test"));
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
	 * Przetwarzanie zdarzeni zaznaczenia wêz³a na drzewie taksonomii
	 * @param event zdarzenie zaznaczenia wêz³a
	 */
	public void processSelection(NodeSelectedEvent event) {
		// odczytaj wybrany wezel
		HtmlTree tree = (HtmlTree) event.getComponent();
		selectedNode = ((TaxElement)tree.getRowData()).getTrace();
		
		//dodaj wezel do listy stringow
		selectionHistory.add(0, selectedNode);
		if(selectionHistory.size()>=8)
			selectionHistory.remove(7);
		
		//dodaj wezel do listy SelectItems
		selectedNodeTokenized = new ArrayList<SelectItem>();
		StringTokenizer token = new StringTokenizer(selectedNode);
		token.nextToken(".");
		while (token.hasMoreTokens()) {
			SelectItem si = new SelectItem(token.nextElement().toString());
			selectedNodeTokenized.add(si);
		}
		selectionHistoryList.add(0, selectedNodeTokenized);
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

	public void setSelectedNode(String selectedNode) {
		this.selectedNode = selectedNode;
	}

	public String getSelectedNode() {
		return selectedNode;
	}

	public void setSelectionHistory(List<String> selectionHistory) {
		this.selectionHistory = selectionHistory;
	}

	public List<String> getSelectionHistory() {
		return selectionHistory;
	}

	public void setSelectedNodeTokenized(List<SelectItem> selectedNodeTokenized) {
		this.selectedNodeTokenized = selectedNodeTokenized;
	}

	public List<SelectItem> getSelectedNodeTokenized() {
		return selectedNodeTokenized;
	}

	public void setSelectionHistoryList(List<List<SelectItem>> selectionHistoryList) {
		this.selectionHistoryList = selectionHistoryList;
	}

	public List<List<SelectItem>> getSelectionHistoryList() {
		return selectionHistoryList;
	}

	public void setTestList(List<SelectItem> testList) {
		this.testList = testList;
	}

	public List<SelectItem> getTestList() {
		return testList;
	}

}