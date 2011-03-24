package pdm.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import pdm.beans.TaxElement;
import pdm.dao.TaxElementDAO;

public class TreeBean {
	private TreeNode<String> rootNode = null;
	private static int counter = 0;
	private TaxElementDAO taxElementDAO;

	public TreeNode<String> getRootNode() {
		if (rootNode == null)
			loadTree();
		return rootNode;
	}

	public void setRootNode(TreeNode<String> rootNode) {
		this.rootNode = rootNode;
	}

	private List<String> selectedNodeChildren = new ArrayList<String>();

	private String nodeTitle;

	public String getNodeTitle() {
		return "nodeTitle";
		// return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	private void loadTree() {
		Vector<TaxElement> elements = taxElementDAO.getObjects();
		
		
		for (int i = 0; i < elements.size();i++)
		{
			for (int i2 = 0;i2 < elements.size(); i2++)
			if (elements.get(i).getParentId() == elements.get(i2).getId())
				elements.get(i2).addChild(elements.get(i).getId(),elements.get(i) );
		}
		
		rootNode = new TreeNodeImpl<String>();
		rootNode.setData("Root");
		
		for (int i = 0; i < elements.size();i++)
		{
			if (elements.get(i).getParentId() ==0)
				rootNode.addChild(elements.get(i).getId(),elements.get(i) );
		}


	}

	private void createNodes(TreeNode<String> root) {
		counter++;

		for (int i = 0; i < 3; i++) {

			TreeNode<String> node = new TreeNodeImpl<String>();
			node.setData("dataLevel/" + i);
			root.addChild(i, node);
			if (counter < 50)

				createNodes(node);

		}

	}

	public void processSelection(NodeSelectedEvent event) {

	}

	public void setTaxElementDAO(TaxElementDAO taxElementDAO) {
		this.taxElementDAO = taxElementDAO;
	}

	public TaxElementDAO getTaxElementDAO() {
		return taxElementDAO;
	}

}