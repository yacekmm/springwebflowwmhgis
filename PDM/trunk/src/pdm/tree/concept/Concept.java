package pdm.tree.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import pdm.Utils.PdmLog;
import pdm.beans.TaxElement;
import pdm.tree.TreeBean;

public class Concept implements Serializable, Comparable<Concept> {
	/**
	 * ID dla serializacji
	 */
	private static final long serialVersionUID = 2377647746487630594L;
	/**
	 * Lista obiektów TaxElement, które tworz¹ ten koncept
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
	
	public Concept(){
		selectedConcept = new ArrayList<TaxElement>();
	}
	
	public void setSelectedConcept(List<TaxElement> selectedConcept) {
		this.selectedConcept = selectedConcept;
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

	public void lockFaces(boolean lockElementFace) {
		for (TaxElement te : selectedConcept) {
			if(!te.getFace().equals("standard"))
				te.setFaceLocked(lockElementFace);
			System.out.println("Element: " + te.getData() + ", face locked = " + te.isFaceLocked());
		}
		
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
	 * Compares Concept basing on the taxonomy ID value
	 */
	@Override
	public int compareTo(Concept c) {
		if(this.getTaxonomyId() == c.getTaxonomyId())
			return 0;
		else if(this.getTaxonomyId() > c.getTaxonomyId())
			return 1;
		else 
			return -1;
	}
	
	/**
	 * zwraca identyfikator korzenia taksonomii do ktorej nalezy dany koncept
	 * @param c Concept którego Id taksonomii nalezy odczytac
	 * @return ID taksonomii
	 */
	private int getTaxId(Concept c){
		int taxId = 0;
		
//		if(id.contains("."))
//			taxId = Integer.parseInt(c.id.substring(0, c.id.indexOf(".")-1));
//		else
//			taxId = Integer.parseInt(c.id);
		
		PdmLog.getLogger().info("ID to parse: " + id + ", parsed to: " + taxId);
		//System.out.println("ID to parse: " + id + ", parsed to: " + taxId);
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
}
