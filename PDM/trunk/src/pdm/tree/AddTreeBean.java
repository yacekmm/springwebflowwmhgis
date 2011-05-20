package pdm.tree;

import java.util.Vector;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.PdmLog;
import pdm.beans.TaxElement;
import pdm.dao.ResultsIndexDAO;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;

public class AddTreeBean implements TreeBeanInterface {

	private static final long serialVersionUID = 4409428227395643479L;
	private TreeNodeImpl<TaxElement> rootNode;
	private TaxElement selectedNode;
	private TaxElementDAO taxElementDAO;

	@Override
	public void processSelection(NodeSelectedEvent event) {
		PdmLog.getLogger().info("processing Add-tree selection");
		HtmlTree tree = (HtmlTree) event.getComponent();
		try {
			selectedNode = (TaxElement) tree.getRowData();

			if (selectedNode.getColor() != null) {
				selectedNode.setColor(Colors.ORANGE0.getC());
				selectedNode.setFace("standard");
			}

			else
				selectedNode.setColor(null);
		} catch (Exception e) {
			e.printStackTrace();
			PdmLog.getLogger().error(e);
		}
	}

	@Override
	public TreeNodeImpl<TaxElement> getRootNode() {
		if (rootNode == null)
			loadTree();
		return rootNode;
	}

	private void loadTree() {
		Vector<TreeNodeImpl<TaxElement>> elements = taxElementDAO
				.getTreeObjects();

		for (int i = 0; i < elements.size(); i++) {
			for (int i2 = 0; i2 < elements.size(); i2++)
				if (elements.get(i).getData().getParentId() == elements.get(i2)
						.getData().getId())
					elements.get(i2).addChild(
							elements.get(i).getData().getId(), elements.get(i));
		}

		rootNode = new TreeNodeImpl<TaxElement>();

		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getData().getParentId() == 0)
				rootNode.addChild(elements.get(i).getData().getId(),
						elements.get(i));
		}
	}

	@Override
	public void setRootNode(TreeNodeImpl<TaxElement> rootNode) {
		this.rootNode = rootNode;

	}

	@Override
	public void setTaxElementDAO(TaxElementDAO taxElementDAO) {
		this.taxElementDAO = taxElementDAO;

	}

	@Override
	public TaxElementDAO getTaxElementDAO() {
		return taxElementDAO;
	}

	@Override
	public void setResultsIndexDAO(ResultsIndexDAO resultsIndexDAO) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultsIndexDAO getResultsIndexDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSearchResultDAO(SearchResultDAO searchResultDAO) {
		// TODO Auto-generated method stub

	}

	@Override
	public SearchResultDAO getSearchResultDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedNode(TaxElement selectedNode) {
		this.selectedNode = selectedNode;

	}

	@Override
	public TaxElement getSelectedNode() {

		return selectedNode;
	}

}
