package pdm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.PdmLog;
import pdm.beans.TaxElement;
import pdm.dao.ResultsIndexDAO;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;
import pdm.tree.concept.Concept;

public class TreeBean implements Serializable {
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * logger u¿ywany do logowania w aplikacji(log4j)
	 */
	
	private TreeNodeImpl<TaxElement> rootNode = null;
	private TaxElementDAO taxElementDAO;
	private ResultsIndexDAO resultsIndexDAO;
	private SearchResultDAO searchResultDAO;
	private String nodeTitle;
	
	private TaxElement selectedNode = null;
	private List<TaxElement> selectedConcept;
	private Concept concept;
	private List<Concept> conceptHistory;
	private int conceptHistorySize = 8;
	
//	private String testValue;

	public TreeBean(){
		concept = new Concept();
		conceptHistory = new ArrayList<Concept>();
		selectedConcept = new ArrayList<TaxElement>();
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
		//usun kolorowanie tymczasowe niezatwierdzonego konceptu
		if(concept.getSelectedConcept().size() != 0){
			for (TaxElement te : concept.getSelectedConcept()) {
				if(te.getFace().equals("orange"))
					te.setFace("standard");
			}
		}
		
		// odczytaj wybrany wezel
		HtmlTree tree = (HtmlTree) event.getComponent();
		selectedNode = (TaxElement)tree.getRowData();
		selectedNode.setFace("orange-0");
		selectedConcept = new ArrayList<TaxElement>();
		concept = new Concept();
		
		//wype³niaj wybrany koncept elementami taksonomii az do rodzica
		StringBuilder sb = new StringBuilder();
		do{
			selectedConcept.add(0, selectedNode);
			sb.insert(0, ".");
			sb.insert(0, selectedNode.getId());
			selectedNode = selectedNode.getTreeHolder().getParent().getData();
		}while(selectedNode != null);
		
		concept.setSelectedConcept(selectedConcept);
		concept.setId(sb.substring(0, sb.length()-1).toString());
		PdmLog.getLogger().info("Selection Listener: " + concept.getName() + ", id: " + concept.getId());
	}
	
	public void conceptConfirmed(String currentFace, String faceToSet){
		System.out.println("actionListener: " + concept.getName().toString());
		
		StringBuilder sb = new StringBuilder();
		TaxElement te = new TaxElement();
		
		//zmien kolorowanie z tymczasowego na staly
		//for (TaxElement te : concept.getSelectedConcept()) {
		for (int i = concept.getSelectedConcept().size()-1; i>=0; i--){
			te = concept.getSelectedConcept().get(i);
			if(te.getFace().contains(currentFace)){
				sb = new StringBuilder();
				sb.append(faceToSet);
				//sb.append("-");
				sb.append(te.getFace().substring(te.getFace().indexOf("-"), te.getFace().length()));
				System.out.println("Changing face from: " + te.getFace() + " to: " + sb.toString());
				te.setFace(sb.toString());
			}
		}
		
//		concept.lockFaces(true);
		
		//jesli koncept juz wczesniej byl wybrany to nadpisz go, zapoibegajac zdublowaniu
		int duplicateIndex = -1;
		for (int i=0; i<conceptHistory.size(); i++) {
			if(((Concept)conceptHistory.get(i)).getId().equals(concept.getId())){
				duplicateIndex = i;
				break;
			}
		}
		if(duplicateIndex>=0)
			conceptHistory.remove(duplicateIndex);
		conceptHistory.add(0, concept);
		
		concept = new Concept();
	}
	
	public void recolour(String elementName){
		String color = "standard";
		boolean recolour = false;
		int index=0;
		StringBuilder sb;
		
		//przejrzyj wszystkie wezly konceptu
		for (TaxElement te : concept.getSelectedConcept()) {
			//jezeli wezel nie jest zablokowany przed kolorwaniem (np. przez inny wybrany wczesniej koncept)
			
			//zaznacz kolorem tymczasowym wybrane wezly
			// jesli user zawezil wybor to odmaluj odznaczone wezly
			if(!te.getFace().equals("standard")){
				te.setFace(color);
				System.out.println("Recolouring from green to standard: " + te.getData() + ", locked = " + te.isFaceLocked());
			}
			
			//jesli dotarles do wezla kliknietego przez uzytkownika zacznij kolorowac
			if(te.getData().equals(elementName)){
				recolour = true;
			}
			//i wezly polozone ponizej zacznij malowac na kolor
			if(recolour){
				sb = new StringBuilder();
				sb.append("orange-");
				sb.append(index++);
				color = sb.toString();
				te.setFace(color);
			}
			System.out.println("Recolouring: " + te.getData() + ", to face: " + te.getFace());
		}
		System.out.println("Recoloured!");
	}
	
	public void editHistConcept(String conceptId) {
		for (Concept c : conceptHistory) {
			if(c.getId().equals(conceptId)){
				concept = c;
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (TaxElement te : concept.getSelectedConcept())
		{
			if(te.getFace().contains("-")){
				sb = new StringBuilder();
				sb.append("orange-");
				sb.append(te.getFace().substring(te.getFace().indexOf("-")+1, te.getFace().length()));
				te.setFace(sb.toString());
			}
		}
		
		concept.lockFaces(false);
	}
	
	public void removeHistConcept(String conceptId) {
		int index = -1;
		for (Concept c : conceptHistory) {
			if(c.getId().equals(conceptId)){
				index=c.getIndex();
				break;
			}
		}
		if(index>=0)
			conceptHistory.remove(index);
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

	public TreeNodeImpl<TaxElement> getRootNode() {
		if (rootNode == null)
			loadTree();
		return rootNode;
	}

	public void setRootNode(TreeNodeImpl<TaxElement> rootNode) {
		this.rootNode = rootNode;
	}

	public String getNodeTitle() {
		return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
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

	public void setConceptHistorySize(int conceptHistorySize) {
		this.conceptHistorySize = conceptHistorySize;
	}

	public int getConceptHistorySize() {
		return conceptHistorySize;
	}

//	public void setTestValue(String testValue) {
//		this.testValue = testValue;
//	}
//
//	public String getTestValue() {
//		return testValue;
//	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConceptHistory(List<Concept> conceptHistory) {
		this.conceptHistory = conceptHistory;
	}

	public List<Concept> getConceptHistory() {
		Collections.sort(conceptHistory);
		return conceptHistory;
	}
}