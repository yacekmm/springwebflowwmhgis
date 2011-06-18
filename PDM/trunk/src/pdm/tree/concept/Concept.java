package pdm.tree.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pdm.Utils.ColorGradient;
import pdm.Utils.Const;
import pdm.Utils.PdmLog;
import pdm.beans.TaxElement;
import pdm.tree.TreeBean;

public class Concept implements Serializable, Comparable<Concept> {
	/**
	 * ID dla serializacji
	 */
	private static final long serialVersionUID = 2377647746487630594L;
	/**
	 * Lista obiektow TaxElement, ktore tworza ten koncept
	 */
	private List<TaxElement> selectedConcept;
	/**
	 * jednolinijkowa nazwa konceptu w postaci 'Location - Inside - Room'
	 */
	private String name;
	/**
	 * id danego konceptu w formacie POBICOS (np. 2.13.45)
	 */
	private String id;
	/**
	 * wyglad tego konceptu przy renderowniu (kolorowanie wybranych konceptow)
	 */
	private String conceptFace;
	/**
	 * indeks tego konceptu na liscie ConceptHistory w TreeBean
	 */
	private int index;
	/**
	 * identyfikator taksonomii z ktorej pochodzi koncept
	 */
	private int taxonomyId = -1;
	/**
	 *  nazwa taksonomii z ktorej pochodzi koncept
	 */
	private String taxonomyName = "";
	/**
	 * przydatna na liscie historii wybranych koncept�w - wskazuje na posortowanej
	 * liscie czy ten koncept jest jako pierwszy z danej taksonomii
	 * i czy powinien w GIU byc wyrenderowany separator oddzielajacy koncepty pochadzace z roznych tax
	 */
	private boolean firstFromThisTax = false;
	
	private List<TaxElementInHistory> confirmedConcept;
	
	private List<TaxElement> conceptChildren;
	private List<SelectItem> conceptChildrenItems;
	
	private boolean hasChildren;
	private SelectItem selectedChild;
	private String selectedChildText;
	//private int selectedChildInt;
	//private static final String PROMPT_TEXT = "Choose to extend...";
	
	public Concept(){
		selectedConcept = new ArrayList<TaxElement>();
		conceptChildren = new ArrayList<TaxElement>();
		conceptChildrenItems = new ArrayList<SelectItem>();
	}
	
	public void setSelectedConcept(List<TaxElement> selectedConcept) {
		this.selectedConcept = selectedConcept;
		
		StringBuilder sb = new StringBuilder();
		for (TaxElement te : selectedConcept) {
			sb.append(te.getId());
			sb.append(".");
		}
		
		this.setId(sb.substring(0, sb.length()-1).toString());
		
		PdmLog.getLogger().info("Created new Concept. Id = " + this.getId());
	}

	public List<TaxElement> getSelectedConcept() {
		return selectedConcept;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();

		for (TaxElement te : selectedConcept) {
			sb.append(te.getData());
			sb.append(" - ");
		}
		if(sb.length()>=3)
			name = sb.substring(0, sb.length()-3).toString();

		return name;
	}

	public void setId(String id) {
		this.id = id;
		this.setTaxonomyId(getTaxId(this));
	}

	public String getId() {
		return id;
	}

	public void setConceptFace(String conceptFace) {
		
		this.conceptFace = conceptFace;
	}

	public String getConceptFace() {
		return conceptFace;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		index = bean.getConceptHistory().indexOf(this);
		return index;
	}

	/**
	 * Compares Concept basing on the taxElements id value
	 */
	@Override
	public int compareTo(Concept c) {
		if(this.getTaxonomyId() == c.getTaxonomyId()){
			PdmLog.getLogger().info("compareTo: te same taxonomie, zaczynam rekurencje");
			int result = iterativeCompare(c, c.getId(), this.getId());
			return result;
		}
		else if(this.getTaxonomyId() > c.getTaxonomyId())
			return 1;
		else 
			return -1;
	}
	
	public int iterativeCompare(Concept c, String id, String thisId){
		int result = 0;
		try{
			if(id.contains(".")){
				StringTokenizer st = new StringTokenizer(id, ".");
				StringTokenizer stThis = new StringTokenizer(thisId, ".");
				if(st.hasMoreElements()){
					if(stThis.hasMoreElements()){
						result = Integer.parseInt(stThis.nextToken()) - Integer.parseInt(st.nextToken());
						if(result == 0){
							result = iterativeCompare(c, id.substring(id.indexOf(".")+1, id.length()),
									thisId.substring(id.indexOf(".")+1, thisId.length()));
						}
						return result;
					}else{
						return -1;
					}
				} else return 1;
			}else{
				
			}
				
		}catch(Exception e){
			PdmLog.getLogger().error("nie udalo sie porownac dwoch elementow w liscie - zwracam awaryjnie '-1'");
			return -1;
		}
		return -1;
	}
	
//	/**
//	 * Compares Concept basing on the taxonomy ID value
//	 */
//	@Override
//	public int compareTo(Concept c) {
//		if(this.getTaxonomyId() == c.getTaxonomyId())
//			return 0;
//		else if(this.getTaxonomyId() > c.getTaxonomyId())
//			return 1;
//		else 
//			return -1;
//	}
	
	/**
	 * zwraca identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 * @param c Concept ktorego Id taksonomii nalezy odczytac
	 * @return ID taksonomii
	 */
	private int getTaxId(Concept c){
		int taxId = 0;
		
		StringTokenizer st = new StringTokenizer(id, ".");
		if(st.hasMoreTokens()){
			taxId = Integer.parseInt(st.nextToken());
		} else {
			taxId = Integer.parseInt(id);
		}
		
		PdmLog.getLogger().info("ID to parse: " + id + ", parsed to: " + taxId);
		return taxId;
	}

	public void setTaxonomyId(int taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

	public int getTaxonomyId() {
		if(taxonomyId < 0)
			taxonomyId = getTaxId(this);
		return taxonomyId;
	}

	public void setFirstFromThisTax(boolean firstFromThisTax) {
		this.firstFromThisTax = firstFromThisTax;
	}

	public boolean isFirstFromThisTax() {
		return firstFromThisTax;
	}

	public void setConceptChildren(List<TaxElement> conceptChildren) {
		this.conceptChildren = conceptChildren;
	}

	public List<TaxElement> getConceptChildren() {
		return conceptChildren;
	}

	public void setConceptChildrenItems(List<SelectItem> conceptChildrenItems) {
		this.conceptChildrenItems = conceptChildrenItems;
	}

	public List<SelectItem> getConceptChildrenItems() {
		conceptChildrenItems = new ArrayList<SelectItem>();
		//conceptChildrenItems.add(new SelectItem("Choose to extend...", "Choose to extend..."));
		conceptChildrenItems.add(new SelectItem(Const.EXTENDER_PROMPT_TEXT, Const.EXTENDER_PROMPT_TEXT));
		for (TaxElement te : conceptChildren) {
			conceptChildrenItems.add(new SelectItem(te.getData(), te.getData()));
		}
		
		return conceptChildrenItems;
	}

	public void setSelectedChild(SelectItem selectedChild) {
		this.selectedChild = selectedChild;
		PdmLog.getLogger().info("adding concept (setting sel item)");
	}

	public SelectItem getSelectedChild() {
		PdmLog.getLogger().info("getting sel item");
		return selectedChild;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public boolean isHasChildren() {
		hasChildren = true;
		if(conceptChildren.size()==0 || selectedConcept.size() == 0)
			hasChildren = false;
		return hasChildren;
	}
	
	public void freezeConceptToHistory(){
		confirmedConcept = new ArrayList<TaxElementInHistory>();
		
		for (TaxElement te : selectedConcept) {
			//confirmedConcept.add(new TaxElementInHistory(te.getData(), te.getFace(), te.getFaceHex(), te.getAbstractionIndex()));
			confirmedConcept.add(new TaxElementInHistory(te));
		}
		
		setElementFaces(ColorGradient.getInstance().getStandardColor());
	}
	
	/**
	 * obsluguje zdarzenie edycji konceptu zatwierdzonego wczesniej
	 * poprzez wyciagniecie go z historii konceptow (odmrozenie)
	 */
	public void unfreezeConceptFromHistory(){
		for (int i=0; i<confirmedConcept.size(); i++) {
			//przywroc abstractionIndex z zamrozonego konceptu
			selectedConcept.get(i).setAbstractionIndex(confirmedConcept.get(i).getAbstractionIndex());
			//przywroc kolor z zamrozonego konceptu
			selectedConcept.get(i).setFace(confirmedConcept.get(i).getColor());
		}
	}

	public void setConfirmedConcept(List<TaxElementInHistory> confirmedConcept) {
		this.confirmedConcept = confirmedConcept;
	}

	public List<TaxElementInHistory> getConfirmedConcept() {
		return confirmedConcept;
	}
	
	/**
	 * ustawia kolor wszsytkich Elementow tego konceptu na podany przez parametr
	 * @param color kolor do ustawienia (forma tekstowa)
	 */
	public void setElementFaces(String color){
		for (TaxElement te : selectedConcept) {
			te.setFace(color);
		}
	}
	
	/**
	 * ustawia abstraction Index wszystkich elementow na domyslny (-1)
	 */
	public void resetAbstractionIndexes(){
		for (TaxElement te : selectedConcept) {
			te.setAbstractionIndex(-1);
		}
	}

	public void setSelectedChildText(String selectedChildText) {
		//prepareToExtendConceptV1(selectedChildText);
		prepareToExtendConceptV2(selectedChildText);
	}

	/**
	 * obsluz zdarzenie wybrania kolejnego elementu rozszerzajacego koncept w edytorze
	 * wersja druga - odwrocony gradient
	 * @param selectedChildText wybrany element do dodania
	 */
	private void prepareToExtendConceptV2(String selectedChildText) {
		if(selectedChildText.equals(Const.EXTENDER_PROMPT_TEXT)){
			PdmLog.getLogger().info("Wybrany element nie nalezy do zbioru dzieci konceptu");
			return;
		}
		
		this.selectedChildText = selectedChildText;
		
		//obsluz zdarzenie rozszerzenia edytowanego konceptu
		PdmLog.getLogger().info("setting sel item string: " + selectedChildText);
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		
		//znajdz element o ktory nalezy rozszerzyc koncept, wsrod dzieci
		TaxElement chosenElement = new TaxElement();
		for (TaxElement te : conceptChildren) {
			if(te.getData().equals(selectedChildText)){
				chosenElement = te;
				break;
			}
		}

		//rozszerz koncept
		bean.extendConceptV2(chosenElement);
		
		//ustal abstraction Indexy na nowo
		bean.updateAbstractionIndexes(bean.findSelectedNode());
		
		//ustaw wybrany koncept na domyslny tekst zachety (zresetuj)
		this.selectedChildText = Const.EXTENDER_PROMPT_TEXT;
	}

	/**
	 * obsluz zdarzenie wybrania kolejnego elementu rozszerzajacego koncept w edytorze
	 * wersja pierwsza - nieodwrocony gradient
	 * @param selectedChildText wybrany element do dodania
	 */
/*	private void prepareToExtendConceptV1(String selectedChildText) {
		if(selectedChildText.equals(PROMPT_TEXT)){
			PdmLog.getLogger().info("Wybrany element nie nalezy do zbioru dzieci konceptu");
			return;
		}
		
		this.selectedChildText = selectedChildText;
		
		//obsluz zdarzenie rozszerzenia edytowanego konceptu
		PdmLog.getLogger().info("setting sel item string: " + selectedChildText);
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		
		//znajdz element o ktory nalezy rozszerzyc koncept, wsrod dzieci
		TaxElement chosenElement = new TaxElement();
		for (TaxElement te : conceptChildren) {
			if(te.getData().equals(selectedChildText)){
				chosenElement = te;
				break;
			}
		}
		
		//ustal kolor na jaki nalezy oznaczyc wybrane dziecko
		TaxElement specificEnd = new TaxElement();
		specificEnd = selectedConcept.get(selectedConcept.size()-1);
		String indexStr = specificEnd.getFace().substring(specificEnd.getFace().length()-1, specificEnd.getFace().length()-0);
		try{
			int index = Integer.parseInt(indexStr) + 1;
			String colorToSet = ColorGradient.getInstance().getNeutralColor() + "-" + index;
			PdmLog.getLogger().info("Setting color after extending to: " + colorToSet);
			bean.extendConcept(chosenElement, colorToSet);
		}catch (Exception e) {
			PdmLog.getLogger().error("Error while extending concept (setting color to default: orange-5)", e);
			bean.extendConcept(chosenElement, ColorGradient.getInstance().getNeutralColor() + "-5");
		}
		
		//ustaw wybrany koncept na domyslny tekst zachety (zresetuj)
		this.selectedChildText = PROMPT_TEXT;
	}
*/
	public String getSelectedChildText() {
		return selectedChildText;
	}

	public void setTaxonomyName(String taxonomyName) {
		this.taxonomyName = taxonomyName;
	}

	public String getTaxonomyName() {
		this.taxonomyName = selectedConcept.get(0).getData();
		return taxonomyName;
	}

	/**
	 * zwroc kolor konceptu w kwalifikatorze poprzez pobranie koloru TaxElementu 
	 * (ostatniego z konceptu, czyli tego najdalej od roota)
	 * @return kolor konceptu wskazujacy czy ten koncept jest WLACZONY  czy WYLACZONY z kwalifikatora
	 */
	public String getConfirmedConceptColor() {
		String foundConceptColor = getConfirmedConcept().get(getConfirmedConcept().size() - 1).getColor();
		if(foundConceptColor.contains("-"))
			foundConceptColor = foundConceptColor.substring(0, foundConceptColor.indexOf("-"));
		return foundConceptColor;
		
	}
}
