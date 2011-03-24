package pdm.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

public class TreeBean {
	private TreeNode<String> rootNode = null;
	private static int counter = 0;
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
		//return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	private void loadTree() {
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// ExternalContext externalContext = facesContext.getExternalContext();
		// InputStream dataStream =
		// externalContext.getResourceAsStream(DATA_PATH);
		try {
			// Properties properties = new Properties();
			// properties.load(dataStream);

			rootNode = new TreeNodeImpl<String>();
			rootNode.setData("Root");
			createNodes(rootNode);
			// addNodes(null, rootNode, properties);

		} finally {
			/*
			 * if (dataStream != null) { try { dataStream.close(); } catch
			 * (IOException e) { externalContext.log(e.getMessage(), e); } }
			 */
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
	public void processSelection(NodeSelectedEvent event)
	{
	}
	}

}