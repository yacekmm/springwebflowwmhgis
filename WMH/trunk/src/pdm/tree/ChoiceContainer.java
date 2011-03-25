package pdm.tree;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.Draggable;
import org.richfaces.component.UIDragSupport;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.event.DropEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeRowKey;

import pdm.beans.TaxElement;

public class ChoiceContainer {
	private List<TreeNode<String>> selectedYesContainer = new ArrayList<TreeNode<String>>();
	public List<TreeNode<String>> getSelectedYesContainer() {
		return selectedYesContainer;
	}
	public void setSelectedYesContainer(List<TreeNode<String>> selectedYesContainer) {
		this.selectedYesContainer = selectedYesContainer;
	}
	private List<TreeNode<String>> selectedNoContainer = new ArrayList<TreeNode<String>>();
	public List<TreeNode<String>> getSelectedNoContainer() {
		return selectedNoContainer;
	}
	public void setSelectedNoContainer(List<TreeNode<String>> selectedNoContainer) {
		this.selectedNoContainer = selectedNoContainer;
	}
	
	
	public void processYesDrop(DropEvent dropEvent)
	{
		TaxElement dragValue  = dropEvent.getDragValue() instanceof TaxElement ? (TaxElement) dropEvent.getDragValue() : null;
		dragValue.setFace("green");
		
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
	    
	    System.err.println("koniec");

	}
	
	public void processNoDrop(DropEvent dropEvent)
	{
		TaxElement dragValue  = dropEvent.getDragValue() instanceof TaxElement ? (TaxElement) dropEvent.getDragValue() : null;
		dragValue.setFace("red");
		
	}

}
