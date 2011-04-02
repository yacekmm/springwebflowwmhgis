package pdm.tree;



import java.util.Vector;
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
    /*private List<TreeNode<String>> selectedNodeChildren = new ArrayList<TreeNode<String>>();
	

	public List<TreeNode<String>> getSelectedNodeChildren() {
		return selectedNodeChildren;
	}

	public void setSelectedNodeChildren(List<TreeNode<String>> selectedNodeChildren) {
		this.selectedNodeChildren = selectedNodeChildren;
	}*/

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
		//return "nodeTitle";
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
	
	


	public void processSelection(NodeSelectedEvent event) {
		//selectedNodeChildren = new ArrayList<TreeNode<String>>();
			//selectedNodeChildren.add(new TaxElement());
			
		/*
	    HtmlTree tree = (HtmlTree) event.getComponent();
	    nodeTitle = (String) tree.getRowData();
	    selectedNodeChildren.clear();
	    TreeNode<String> currentNode = tree.getModelTreeNode(tree.getRowKey());
	    TaxElement demoCurrentNodeImpl = currentNode instanceof TaxElement ? (TaxElement) currentNode : null;
	    if (currentNode.isLeaf() && (demoCurrentNodeImpl != null && demoCurrentNodeImpl.getType().toLowerCase().equals("leaf") || demoCurrentNodeImpl == null )) {      
	        selectedNodeChildren.add(currentNode);
	    } else {
	        Iterator<Map.Entry<Object, TreeNode<String>>> it = currentNode.getChildren();
	        while (it != null && it.hasNext()) {
	        Map.Entry<Object, TreeNode<String>> entry = it.next();
	        selectedNodeChildren.add(entry.getValue());
	        }
	    }*/

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

}