package pdm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.ColorGradient;
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
				te.setFace(ColorGradient.getInstance().standardColor);
			}
		}
		
		// odczytaj wybrany wezel
		HtmlTree tree = (HtmlTree) event.getComponent();
		try{
			selectedNode = (TaxElement)tree.getRowData();
			//selectedNode.setFace("orange-0");
			selectedConcept = new ArrayList<TaxElement>();
			concept = new Concept();
			extractConceptChildren(selectedNode);
			
			//wype³niaj wybrany koncept elementami taksonomii az do rodzica
			//StringBuilder sb = new StringBuilder();
			TaxElement tmpNode = selectedNode;
			do{
				selectedConcept.add(0, tmpNode);
				//sb.insert(0, ".");
				//sb.insert(0, selectedNode.getId());
				tmpNode = tmpNode.getTreeHolder().getParent().getData();
			}while(tmpNode != null);
			
			concept.setSelectedConcept(selectedConcept);
			recolour(selectedNode.toString());
			//concept.setId(sb.substring(0, sb.length()-1).toString());
			PdmLog.getLogger().info("Selection Listener: " + concept.getName() + ", id: " + concept.getId());
		}catch (Exception e) {
			PdmLog.getLogger().error("Blad przy obsludze zaznaczenia drzewa: " + e.getStackTrace().toString());
		}
	}

	private void extractConceptChildren(TaxElement specificEnd) {
		Iterator<Entry<Object, TreeNode<TaxElement>>> test = specificEnd.getTreeHolder().getChildren();
		concept.setConceptChildren(new ArrayList<TaxElement>());
		while(test.hasNext()){
			concept.getConceptChildren().add((TaxElement)test.next().getValue().getData());
		}
	}
	
	public void conceptConfirmed(String currentFace, String faceToSet){
		if(concept.getSelectedConcept().size() == 0){
			PdmLog.getLogger().warn("Koncept pusty. anuluje potwierdzanie");
			return;
		}
		
		PdmLog.getLogger().info("actionListener: " + concept.getName().toString());
		
		StringBuilder sb = new StringBuilder();
		TaxElement te = new TaxElement();
		
		//usun kolorowanie tymczasowe (pomaranczowe) i zastap go kolorem zielonym ale nie w drzewie tylko w kwalifikatorach
		for (int i = concept.getSelectedConcept().size()-1; i>=0; i--){
			te = concept.getSelectedConcept().get(i);
			if(te.getFace().contains(currentFace)){
				sb = new StringBuilder();
				sb.append(faceToSet);
				sb.append(te.getFace().substring(te.getFace().indexOf("-"), te.getFace().length()));
				PdmLog.getLogger().info("Changing face from: " + te.getFace() + " to: " + sb.toString());
				//te.setFaceInHistory(sb.toString());
				te.setFace(sb.toString());
				//te.setFace(ColorGradient.getInstance().standardColor);
			}
		}
		
		//zachowaj stan kolorów elementów konceptu w celu przechowania go w historii
		concept.freezeConceptToHistory();
		
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
		conceptHistory.add(concept);
		
		concept = new Concept();
	}
	
	public void recolour(String elementName){
		String color = ColorGradient.getInstance().standardColor;
		boolean recolour = false;
		int index=0;
		StringBuilder sb;
		
		//przejrzyj wszystkie wezly konceptu
		for (TaxElement te : concept.getSelectedConcept()) {
			//jezeli wezel nie jest zablokowany przed kolorwaniem (np. przez inny wybrany wczesniej koncept)
			
			//zaznacz kolorem tymczasowym wybrane wezly
			// jesli user zawezil wybor to odmaluj odznaczone wezly
			if(!te.getFace().equals(ColorGradient.getInstance().standardColor)){
				te.setFace(color);
				PdmLog.getLogger().info("Recolouring from green to standard: " + te.getData());
			}
			
			//jesli dotarles do wezla kliknietego przez uzytkownika zacznij kolorowac
			if(te.getData().equals(elementName)){
				recolour = true;
			}
			//i wezly polozone ponizej zacznij malowac na kolor
			if(recolour){
				sb = new StringBuilder();
				sb.append(ColorGradient.getInstance().neutralColor);
				sb.append("-");
				sb.append(index++);
				color = sb.toString();
				te.setFace(color);
			}
			PdmLog.getLogger().info("Recolouring: " + te.getData() + ", to face: " + te.getFace());
		}
		PdmLog.getLogger().info("Recoloured!");
	}
	
	public void editHistConcept(String conceptId) {
		for (Concept c : conceptHistory) {
			if(c.getId().equals(conceptId)){
				PdmLog.getLogger().info("Zmieniam koncept z " + concept.getName() + " na " + c.getName());
				concept = c;
				selectedConcept = concept.getSelectedConcept();
				concept.unfreezeConceptFromHistory();
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (TaxElement te : concept.getSelectedConcept())
		{
			if(te.getFace().contains("-")){
				sb = new StringBuilder();
				sb.append(ColorGradient.getInstance().neutralColor);
				sb.append("-");
				sb.append(te.getFace().substring(te.getFace().indexOf("-")+1, te.getFace().length()));
				te.setFace(sb.toString());
			}
		}
	}
	
	public void removeHistConcept(String conceptId) {
		int index = -1;
		for (Concept c : conceptHistory) {
			if(c.getId().equals(conceptId)){
				index=c.getIndex();
				break;
			}
		}
		if(index>=0){
			conceptHistory.get(index).setElementFaces(ColorGradient.getInstance().standardColor);
			conceptHistory.remove(index);
		}
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
		
		int lastTaxId = -1;
		for (Concept c : conceptHistory) {
			if(c.getTaxonomyId() != lastTaxId)
				c.setFirstFromThisTax(true);
			else c.setFirstFromThisTax(false);
			lastTaxId = c.getTaxonomyId();
		}
		return conceptHistory;
	}
	
	public void valueChange(ValueChangeEvent arg0)
			throws AbortProcessingException {
		PdmLog.getLogger().info("Processing value change event...");
	}

	public void cutConcept(String startingElementName) {
		int elementIndex=-99;
		for(int i=0; i<concept.getSelectedConcept().size(); i++){
			if(concept.getSelectedConcept().get(i).getData().equals(startingElementName)){
				elementIndex = i;
				break;
			}
		}
		if(elementIndex<0){
			PdmLog.getLogger().error("niepowodzenie przyciecia konceptu - brak elementu '" + startingElementName + "' w koncepcie");
			return;
		}
		
		concept.setElementFaces(ColorGradient.getInstance().standardColor);
		//jesli usunieto korzen - wyczysc caly koncept
		if(elementIndex==0){
			PdmLog.getLogger().info("creating new concept");
			//odkoloruj to co bylo pokorowane
			concept.setConceptFace(ColorGradient.getInstance().standardColor);
			concept = new Concept();
		} 
		
		// w przeciwnym wypadku usun kolejne koncepty
		else{
			while(elementIndex < selectedConcept.size()){
				//zmien kolor usuwanego elementu na standard
				selectedConcept.get(elementIndex).setFace(ColorGradient.getInstance().standardColor);
				selectedConcept.remove(elementIndex);
			}
//			for(int i = selectedConcept.size()-1; i>= elementIndex;i--){
//				//zmien kolor usuwanego elementu na standard
//				selectedConcept.get(i).setFace(ColorGradient.getInstance().standardColor);
//				selectedConcept.remove(i);
//			}
			
			//rozwijalna lista to teraz beda dzieci
			concept.setSelectedConcept(selectedConcept);
			selectedNode = selectedConcept.get(elementIndex-1);
			extractConceptChildren(selectedNode);
			
			//przekoloruj gradient pozostalych konceptow (bo na pewno zostal uciety koniec gradientu przy usuwaniu elementu)
			boolean onlyStandardColorLeft = true;
			for (int i=0; i<concept.getSelectedConcept().size()-1; i++) {
				if(!concept.getSelectedConcept().get(i).getFace().equals(ColorGradient.getInstance().standardColor)){
					recolour(concept.getSelectedConcept().get(i).toString());
					onlyStandardColorLeft = false;
					break;
				}
			}
			
			//jesli okazalo sie ze zostaly tylko koncepty w kolorze standard to trzeba cos pokolorowac na kolor neutralny
			if(onlyStandardColorLeft)
				recolour(concept.getSelectedConcept().get(concept.getSelectedConcept().size()-1).toString());
		}
	}
	
	public void extendConcept(String newElementName){
		PdmLog.getLogger().info("Got the new Element!!! It is: " + newElementName);
	}
}