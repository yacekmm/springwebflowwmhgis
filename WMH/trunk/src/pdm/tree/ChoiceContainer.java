package pdm.tree;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.Draggable;
import org.richfaces.event.DropEvent;
import org.richfaces.model.TreeNode;

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
	
	
	public void processYesDrop(DropEvent event)
	{
		Draggable i = event.getDraggableSource();
		System.err.println("cos");
	}
	
	public void processNoDrop(DropEvent event)
	{
		
	}

}
