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
	 * jednolinijkowa nazwa konceptu w postaci 'Location - Inside - Room' (dla potrzeb debugowania)
	 */
	private String name;
	/**
	 * id danego konceptu w formacie POBICOS (np. 2.13.45)
	 */
	private String id;
	/**
	 * wyglad tego konceptu przy renderowniu (kolorowanie wybranych konceptow)
	 */
	//TODO: jacek: jesli dziala to usun te zmienna zamiast komentowania jej
	//private String conceptFace;
	/**
	 * indeks tego konceptu na liscie ConceptHistory w TreeBean. potrzebny przy 
	 * grupowaniu konceptow z tej samj taksonomii
	 */
	//TODO: jacek: jesli dziala to usun te zmienna zamiast komentowania jej
	//private int index;
	/**
	 * identyfikator taksonomii z ktorej pochodzi koncept. potrzebny do grupowania kwalifikatorow
	 * w zaleznosi od tego z ktorej taksonomii pochodza
	 */
	private int taxonomyId = -1;
	/**
	 *  nazwa taksonomii z ktorej pochodzi koncept
	 */
	private String taxonomyName = "";
	/**
	 * przydatna na liscie historii wybranych konceptow - wskazuje na posortowanej
	 * liscie czy ten koncept jest jako pierwszy z danej taksonomii
	 * i czy powinien w GUI byc wyrenderowany separator oddzielajacy koncepty pochadzace z roznych tax
	 */
	private boolean firstFromThisTax = false;
	/**
	 * stan konceptu w kaim jest on wlaczony do kwalifikatora obiektow.
	 * czyli stan konceptu i wszystkich jego TaxElementow jaki byl w chwili jego zatwierdzenia 
	 */
	private List<TaxElementInHistory> confirmedConcept;
	/**
	 * lista dzieci obecnego konceptu (jego najbardziej specyficznego elementu)
	 */
	private List<TaxElement> conceptChildren;
	/**
	 * lista dzieci obecnego konceptu (jego najbardziej specyficznego elementu), bdaca kopia 'conceptChildren', 
	 * ale skladajaca sie z obiektow latwych do prezentacji w interfejsie w obiekcie typu DropList (SelectItem)
	 */
	private List<SelectItem> conceptChildrenItems;
	/**
	 * flaga wskazujaca czy obecny koncept ma dzieci (uzywane do podejmpowania decyzji o renderowaniu w 
	 * interfejsie decyzji czy wyswietlac pole z dziecmi konceptu) 
	 */
	private boolean hasChildren;
	/**
	 * obiekt z listy conceptChildItems, ktory zostal wybrany przez uzytkownika z poziomu interfejsu
	 */
	private SelectItem selectedChild;
	/**
	 * obiekt z listy conceptChildItems, ktory zostal wybrany przez uzytkownika z poziomu interfejsu
	 * (w formie zwyklego tekstu)
	 */
	private String selectedChildText;

	/**
	 * konstruktor domyslny
	 */
	public Concept(){
		selectedConcept = new ArrayList<TaxElement>();
		conceptChildren = new ArrayList<TaxElement>();
		conceptChildrenItems = new ArrayList<SelectItem>();
	}
	
	/**
	 * ustawia wartosc zmiennej przechowujacej aktualnie edytowany koncept
	 * @param selectedConcept koncept aktualnie edytowany
	 */
	public void setSelectedConcept(List<TaxElement> selectedConcept) {
		this.selectedConcept = selectedConcept;
		
		//ustal identyfikator konceptu
		StringBuilder sb = new StringBuilder();
		for (TaxElement te : selectedConcept) {
			sb.append(te.getId());
			sb.append(".");
		}
		this.setId(sb.substring(0, sb.length()-1).toString());
		
		//ustal nazwe konceptu
		setConceptName();
		
		//ustal nazwe taksonomii do ktorej nalezy koncept
		this.taxonomyName = selectedConcept.get(0).getData();
		
		PdmLog.getLogger().info("Created new Concept. Id = " + this.getId());
	}

	/**
	 * zwraca wartosc zmiennej przechowujacej aktualnie edytowany koncept
	 * @return wartosc zmiennej przechowujacej aktualnie edytowany koncept
	 */
	public List<TaxElement> getSelectedConcept() {
		return selectedConcept;
	}

	/**
	 * ustawia nazwe aktualnie edytowanego konceptu (forma root-child1-child2)
	 */
	public void setConceptName() {
		StringBuilder sb = new StringBuilder();

		for (TaxElement te : selectedConcept) {
			sb.append(te.getData());
			sb.append(" - ");
		}
		if(sb.length()>=3)
			name = sb.substring(0, sb.length()-3).toString();
	}
	
	/**
	 *  zwraca nazwe aktualnie edytowanego konceptu (forma root-child1-child2)
	 * @return nazwa aktualnie edytowanego konceptu (forma root-child1-child2)
	 */
	public String getName() {
		return name;
	}

	/**
	 * ustawia id konceptu (postaci 1.2.3...) i id taksonomii do ktorej nalezy koncept
	 * @param id id konceptu ktore nalezy ustawic
	 */
	public void setId(String id) {
		this.id = id;
		this.setTaxonomyId(getTaxId(this));
	}

	/**
	 * zwraca id konceptu (postaci 1.2.3...)
	 * @return id konceptu (postaci 1.2.3...)
	 */
	public String getId() {
		return id;
	}

//	public void setConceptFace(String conceptFace) {
//		
//		this.conceptFace = conceptFace;
//	}
//
//	public String getConceptFace() {
//		return conceptFace;
//	}

//	public void setIndex(int index) {
//		this.index = index;
//	}
//
//	public int getIndex() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
//		index = bean.getConceptHistory().indexOf(this);
//		return index;
//	}

	/**
	 * Porownywanie konceptow na bazie identyfikatorow TaxElementow z ktorych sie skladaja
	 * (w celu sortowania kwalifikatora obiektow)
	 */
	@Override
	public int compareTo(Concept c) {
		//sortuj rekurencyjnie w obrebie konceptow pochodzacych z jednej taksonomii
		if(this.getTaxonomyId() == c.getTaxonomyId()){
			return 0;
			//wariant z sortowaniem rekurencyjnym elementow z tej samej taksonomii
			/*
			//jesli element jest rootem to zawsze jest najwiekszy
			if(!c.getId().contains("."))
				return 1;
			
			PdmLog.getLogger().info("compareTo: te same taxonomie, zaczynam rekurencje");
			int result = iterativeCompare(c, c.getId(), this.getId());
			return result;
			*/
		}
		//porownaj konceptyh z roznych taksonomii
		else if(this.getTaxonomyId() > c.getTaxonomyId())
			return 1;
		else 
			return -1;
	}
	
	/**
	 * funkcja rekurencyjnie porownuje koncepty pochodzace z tej samej taksonomii (kryterium: id)
	 * @param c koncept z ktorym porownywany jest koncept 'this'
	 * @param id identyfikator konceptu c
	 * @param thisId identyfikator konceptu 'this'
	 * @return wynik porownania
	 */
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
							String idForNext = "", thisIdForNext  = "";
							boolean errorFlag = false;
							if(id.contains("."))
								idForNext = id.substring(id.indexOf(".")+1, id.length());
							else errorFlag = true;
							
							if(thisId.contains("."))
								thisIdForNext = thisId.substring(id.indexOf(".")+1, thisId.length());
							else errorFlag = true;
							
							if(errorFlag){
								if(id.contains(thisId))
									return -1;
								else if(thisId.contains(id))
									return 1;
								else return 0;
							}
							
							result = iterativeCompare(c, idForNext, thisIdForNext);
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
	
	/**
	 * wylicza identyfikator korzenia taksonomii do ktorej nalezy dany koncept
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
		
		PdmLog.getLogger().info("Computing taxonomy Id: ID to parse: " + id + ", parsed to: " + taxId);
		return taxId;
	}

	/**
	 * ustawia identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 * @param taxonomyId identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 */
	public void setTaxonomyId(int taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

	/**
	 * pobiera identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 * @return identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 */
	public int getTaxonomyId() {
		if(taxonomyId < 0)
			taxonomyId = getTaxId(this);
		return taxonomyId;
	}

	/**
	 * ustawia flage wskazujaca czy koncept jest uznany jako pierwszy z kolejnej taksonomii 
	 * na posortowanej liscie konceptow 
	 * @param firstFromThisTax flaga wskazujaca czy koncept jest uznany jako pierwszy z kolejnej taksonomii 
	 * na posortowanej liscie konceptow
	 */
	public void setFirstFromThisTax(boolean firstFromThisTax) {
		this.firstFromThisTax = firstFromThisTax;
	}

	/**
	 * pobiera flage wskazujaca czy koncept jest uznany jako pierwszy z kolejnej taksonomii 
	 * na posortowanej liscie konceptow
	 * @return flaga wskazujaca czy koncept jest uznany jako pierwszy z kolejnej taksonomii 
	 * na posortowanej liscie konceptow
	 */
	public boolean isFirstFromThisTax() {
		return firstFromThisTax;
	}

	/**
	 * ustawia liste dzieci aktualnego konceptu i buduje liste dzieci jako obiektow SelectItems
	 * @param conceptChildren lista dzieci aktualnego konceptu 
	 */
	public void setConceptChildren(List<TaxElement> conceptChildren) {
		this.conceptChildren = conceptChildren;
		
		conceptChildrenItems = new ArrayList<SelectItem>();
		conceptChildrenItems.add(new SelectItem(Const.EXTENDER_PROMPT_TEXT, Const.EXTENDER_PROMPT_TEXT));
		for (TaxElement te : conceptChildren) {
			conceptChildrenItems.add(new SelectItem(te.getData(), te.getData()));
		}
	}

	/**
	 * pobiera liste dzieci aktualnego konceptu 
	 */
	public List<TaxElement> getConceptChildren() {
		return conceptChildren;
	}

	/**
	 * ustawia liste dzieci aktualnego konceptu jako SelectItems 
	 * @param conceptChildrenItems lista dzieci aktualnego konceptu 
	 */
	public void setConceptChildrenItems(List<SelectItem> conceptChildrenItems) {
		this.conceptChildrenItems = conceptChildrenItems;
	}

	/**
	 * pobiera liste dzieci aktualnego konceptu jako SelectItems 
	 * @return lista dzieci aktualnego konceptu 
	 */
	public List<SelectItem> getConceptChildrenItems() {
		return conceptChildrenItems;
	}
	
	/**
	 * ustaw wybrane w interfejsie dziecko konceptu
	 * @param selectedChild wybrane w interfejsie dziecko konceptu
	 */
	public void setSelectedChild(SelectItem selectedChild) {
		this.selectedChild = selectedChild;
		PdmLog.getLogger().info("Przechwytuje wybrane dziecko konceptu...");
	}

	/**
	 * pobiera wybrane w interfejsie dziecko konceptu
	 * @return wybrane w interfejsie dziecko konceptu
	 */
	public SelectItem getSelectedChild() {
		PdmLog.getLogger().info("Pobieranie wybranego dziecka edytowanego konceptu");
		return selectedChild;
	}

	/**
	 * ustaw flage mowiaca czy edytowany koncept ma dzieci 
	 * @param hasChildren flaga mowiaca czy edytowany koncept ma dzieci
	 */
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	/**
	 * pobierz flage mowiaca czy edytowany koncept ma dzieci
	 * @return flaga mowiaca czy edytowany koncept ma dzieci
	 */
	public boolean isHasChildren() {
		hasChildren = true;
		if(conceptChildren.size()==0 || selectedConcept.size() == 0)
			hasChildren = false;
		return hasChildren;
	}
	
	/**
	 * zachowuje (zamraza) stan pokolorowania edytowanego konceptu w chwili zatwierdzenia 
	 * go do kwalifikatora obiektow
	 */
	public void freezeConceptToHistory(){
		confirmedConcept = new ArrayList<TaxElementInHistory>();
		
		for (TaxElement te : selectedConcept) {
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

	/**
	 * ustawia zmienna przechowujaca zatwierdzony koncept
	 * @param confirmedConcept zmienna przechowujaca zatwierdzony koncept
	 */
	public void setConfirmedConcept(List<TaxElementInHistory> confirmedConcept) {
		this.confirmedConcept = confirmedConcept;
	}

	/**
	 * zwraca wartosc zmiennej przechowujacej zatwierdzony koncept
	 * @return wartosc zmiennej przechowujacej zatwierdzony koncept
	 */
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

	/**
	 * ustawia nazwe wybranego dziecka konceptu, gdy zostalo ono wybrane z poziomu interfejsu
	 * i rozszerzenie konceptu o wybrane dziecko
	 * @param selectedChildText nazwa wybranego dziecka konceptu
	 */
	public void setSelectedChildText(String selectedChildText) {
		//prepareToExtendConceptV1(selectedChildText);
		this.selectedChildText = selectedChildText;
		prepareToExtendConceptV2(selectedChildText);
	}

	/**
	 * obsluz zdarzenie wybrania kolejnego elementu rozszerzajacego koncept w edytorze.
	 * wersja druga - odwrocony gradient
	 * @param selectedChildText wybrany element do dodania
	 */
	private void prepareToExtendConceptV2(String selectedChildText) {
		if(selectedChildText.equals(Const.EXTENDER_PROMPT_TEXT)){
			PdmLog.getLogger().info("Wybrany element nie nalezy do zbioru dzieci konceptu");
			return;
		}
		
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

	/**
	 * ustaw nazwe taksonomii do ktorej nalezy koncept
	 * @param taxonomyName nazwa taksonomii do ktorej nalezy koncept
	 */
	public void setTaxonomyName(String taxonomyName) {
		this.taxonomyName = taxonomyName;
	}

	/**
	 * pobierz nazwe taksonomii do ktorej nalezy koncept
	 * @return nazwa taksonomii do ktorej nalezy koncept
	 */
	public String getTaxonomyName() {
		//this.taxonomyName = selectedConcept.get(0).getData();
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
