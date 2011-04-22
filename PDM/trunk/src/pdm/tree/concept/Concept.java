package pdm.tree.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import pdm.beans.TaxElement;
import pdm.tree.TreeBean;

public class Concept implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2377647746487630594L;
	private List<TaxElement> selectedConcept;
	private String name;
	private String id;
	private String conceptFace;
	private int index;
	
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
}
