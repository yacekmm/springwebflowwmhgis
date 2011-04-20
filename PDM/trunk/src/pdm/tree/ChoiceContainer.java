package pdm.tree;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.event.DropEvent;



import pdm.beans.TaxElement;

public class ChoiceContainer {
	private List<TaxElement> selectedYesContainer = new ArrayList<TaxElement>();
	
	private List<TaxElement> selectedNoContainer = new ArrayList<TaxElement>();
	
	public void processYesDrop(DropEvent dropEvent)
	{
		TaxElement dragValue  = dropEvent.getDragValue() instanceof TaxElement ? (TaxElement) dropEvent.getDragValue() : null;
		dragValue.setFace("green");
		selectedYesContainer.add(dragValue);
		
		
		
		/*// resolve drag destination attributes
	    UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getSource() : null;
	    UITree destTree = destNode != null ? destNode.getUITree() : null;
	    TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDropValue() : null;
	    TreeNode droppedInNode = dropNodeKey != null ? destTree.getTreeNode(dropNodeKey) : null;

	    // resolve drag source attributes
	    UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getDraggableSource() : null;
	    UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
	    TreeRowKey dragNodeKey = (dropEvent.getDragValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDragValue() : null;
	    TreeNode draggedNode = dragNodeKey != null ? srcTree.getTreeNode(dragNodeKey) : null;
	    if (dropEvent.getDraggableSource() instanceof UIDragSupport && srcTree == null && draggedNode == null && dropEvent.getDragValue() instanceof TreeNode) {        
	        srcTree = destTree;
	        draggedNode = (TreeNode) dropEvent.getDragValue();
	        dragNodeKey = srcTree.getTreeNodeRowKey(draggedNode) instanceof TreeRowKey ? (TreeRowKey) srcTree.getTreeNodeRowKey(draggedNode) : null;
	    }*/
	    

	}
	
	public void processNoDrop(DropEvent dropEvent)
	{
		TaxElement dragValue  = dropEvent.getDragValue() instanceof TaxElement ? (TaxElement) dropEvent.getDragValue() : null;
		dragValue.setFace("red");
		selectedNoContainer.add(dragValue);
	}

	public void setSelectedYesContainer(List<TaxElement> selectedYesContainer) {
		this.selectedYesContainer = selectedYesContainer;
	}

	public List<TaxElement> getSelectedYesContainer() {
		return selectedYesContainer;
	}

	public void setSelectedNoContainer(List<TaxElement> selectedNoContainer) {
		this.selectedNoContainer = selectedNoContainer;
	}

	public List<TaxElement> getSelectedNoContainer() {
		return selectedNoContainer;
	}

}
