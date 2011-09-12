package pdm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;
import org.richfaces.model.TreeNodeImpl;
import pdm.Utils.ColorGradient;
import pdm.Utils.PdmLog;
import pdm.dao.Id;
import pdm.dao.TaxElementDAO;

/**
 * Klasa mapujaca instacje elementu taksonomii na baze danych
 * 
 * @author pkonstanczuk
 */
public class TaxElement implements Id, Serializable, Comparable<Object> {
	private static TaxElementDAO taxElementDAO;
	private Set<TaxElement> children;
	private TaxElement parent;
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = 6913477020202058253L;
	/**
	 * Id
	 */
	private Integer Id;
	/**
	 * Id rodziaca
	 */
	// private Integer ParentId;
	/**
	 * Nazwa,opis
	 */
	private String data;
	/**
	 * Odwolanie do drzewa(potrzeby GUI)
	 */
	private TreeNodeImpl<TaxElement> treeHolder;
	/**
	 * Ciag znakow repreezntujacy lokalizacje instancji w drzewiw taksonomii
	 */
	private String trace;
	/**
	 * Kolor taksonomii przy zaznaczaniu
	 */
	private String color = null;
	/**
	 * Id korzenia taksonomii
	 */
	private Integer rootId;
	/**
	 * Przypisane rezultatow wyszukiwania powiazanych z ta instancja taksonomii
	 */
	private Set<SearchResult> searchResults = new HashSet<SearchResult>(0);
	/**
	 * Flaga informujaca o wybraniu elementu z drzewwa
	 */
	private boolean selected = false;
	/**
	 * wsparcie dla konceptow abstrakcyjnych (nasycenie kooru w gradiencie)
	 */
	private int abstractionIndex = -1;

	/**
	 * getter color
	 * 
	 * @return
	 */
	public String getColor() {
		return color;
	}

	/**
	 * setter color
	 * 
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * tekstowa nazwa stylu ktory bedzie uzywany do wyboru koloru dla
	 * prezentacji elementu w widoku (np. orange-0, red-3)
	 */
	private String face;
	/**
	 * Hexadecymalny odpowiednik tego co jest zapisane w face. zdefiniowane na
	 * podstawie danych statycznych z klasy ColorGradient
	 */
	private String faceHex;

	/**
	 * getter trace
	 * 
	 * @param tr
	 */
	public void setTrace(String tr) {
		this.trace = tr;
	}

	public String getTraceLazy() {
		if (taxElementDAO == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			taxElementDAO = (TaxElementDAO) context.getApplication()
					.evaluateExpressionGet(context, "#{taxElementDAO}",
							TaxElementDAO.class);
		}
		return taxElementDAO.get(getId()).getTrace();
	}

	/**
	 * setter trace
	 * 
	 * @return
	 */
	public String getTrace() {
		try {

			StringBuilder sb = new StringBuilder();
			if (this.data != null) {
				sb.append(this.data);
			}
			// Object o = this;
			if (treeHolder.getParent() != null) {
				if (treeHolder.getParent().getData() != null) {
					sb.insert(0,
							treeHolder.getParent().getData().getTrace() + '.');
				}
			}
			trace = sb.toString();
			return trace;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Getter face
	 * 
	 * @return
	 */
	public String getFace() {
		return face;
	}

	/**
	 * Setter face
	 * 
	 * @param face
	 */
	public void setFace(String face) {
		this.face = face;
		faceHex = mapTextFaceToHexV2(face);
	}

	/**
	 * Getter id
	 */
	@Override
	public Integer getId() {
		return Id;
	}

	/**
	 * Setter id
	 */
	@Override
	public void setId(Integer id) {
		Id = id;
	}

	/**
	 * Getter parentId
	 * 
	 * @return
	 */
	public Integer getParentId() {
		if (parent != null)
			return parent.getId();
		return 0;
	}

	/**
	 * Setter data
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Getter data
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * Setter treeHolder
	 * 
	 * @param treeHolder
	 */
	public void setTreeHolder(TreeNodeImpl<TaxElement> treeHolder) {
		this.treeHolder = treeHolder;
	}

	/**
	 * Getter TreeHolder
	 * 
	 * @return
	 */
	public TreeNodeImpl<TaxElement> getTreeHolder() {
		return treeHolder;
	}

	/**
	 * Setter FaceHex
	 * 
	 * @param faceHex
	 */
	public void setFaceHex(String faceHex) {
		this.faceHex = faceHex;
	}

	/**
	 * Getter FaceHex
	 * 
	 * @return
	 */
	public String getFaceHex() {
		// faceHex = mapTextFaceToHexV1(face);
		// faceHex = mapTextFaceToHexV2(face);

		return faceHex;
	}

	/**
	 * zwraca wartosc coloru w HEX, na podstawie stylu w interfejsie wersja
	 * druga - gradient odwrocony
	 * 
	 * @param textFace
	 * @return
	 */
	private String mapTextFaceToHexV2(String textFace) {
		String hexResult = "";
		if (textFace == null) {
			PdmLog.getLogger().error(
					"face byla nullem. ustawiam domyslnie na standardowa");
			textFace = ColorGradient.getInstance().getStandardColor();
		}

		if (textFace.contains("-")) {
			String color = textFace.substring(0, textFace.indexOf("-"));
			String colorToSet = "";

			if (color.equalsIgnoreCase(ColorGradient.getInstance()
					.getIncludedColor())) {
				colorToSet = ColorGradient.colorGradient
						.getColorGradientGreen().get(abstractionIndex);
			} else if (color.equalsIgnoreCase(ColorGradient.getInstance()
					.getExcludedColor())) {
				colorToSet = ColorGradient.colorGradient.getColorGradientRed()
						.get(abstractionIndex);
			} else if (color.equalsIgnoreCase(ColorGradient.getInstance()
					.getNeutralColor())) {
				colorToSet = ColorGradient.colorGradient
						.getColorGradientNeutral().get(abstractionIndex);
			}

			hexResult = colorToSet;
			PdmLog.getLogger().info(
					"Ustawiam kolor '" + colorToSet + "' taxElementu: "
							+ this.data);
		} else if (textFace.equals(ColorGradient.getInstance()
				.getStandardColor())) {
			PdmLog.getLogger().info(
					"Ustawiam standardowy kolor taxElementu dla: " + this.data);
			hexResult = ColorGradient.getInstance().getStandardColor();
		} else {
			PdmLog.getLogger()
					.warn("Problem z przetlumaczeniem koloru TaxElementu na wartosc typu HEX");
			hexResult = ColorGradient.getInstance().getStandardColor();
		}

		return hexResult;
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		return this.data;
	}

	/**
	 * Getter rootId
	 * 
	 * @return
	 */
	public Integer getRootId() {
		if (rootId == null)
			getParentTree();

		return rootId;
	}

	/**
	 * Getter ParentTree
	 * 
	 * @return
	 */
	public List<TaxElement> getParentTree() {
		List<TaxElement> parentTree = new ArrayList<TaxElement>();
		// if (parentTree == null) {
		// parentTree = new ArrayList<TaxElement>();
		parentTree.add(this);

		TaxElement tmp = this;

		while (true) {
			if (tmp.getTreeHolder().getParent() != null
					&& tmp.getTreeHolder().getParent().getData() != null) {
				parentTree.add(tmp.treeHolder.getParent().getData());
				tmp = tmp.treeHolder.getParent().getData();

			} else {
				rootId = tmp.getId();
				break;
			}
		}
		// }
		Collections.reverse(parentTree);
		return parentTree;
	}

	/**
	 * Setter Selected
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Getter Selected
	 * 
	 * @return
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Setter SearchResult
	 * 
	 * @param searchResults
	 */
	public void setSearchResults(Set<SearchResult> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * Getter SearchResult
	 * 
	 * @return
	 */
	public Set<SearchResult> getSearchResults() {
		return searchResults;
	}

	/**
	 * Setter AbstractionIndex
	 * 
	 * @param abstractionIndex
	 */
	public void setAbstractionIndex(int abstractionIndex) {
		this.abstractionIndex = abstractionIndex;
	}

	/**
	 * Getter AbstractionIndex
	 * 
	 * @return
	 */
	public int getAbstractionIndex() {
		return abstractionIndex;
	}

	@Override
	public int compareTo(Object bo) {
		if (bo instanceof TaxElement) {
			TaxElement o = (TaxElement) bo;
			if (o.getId() == this.getId())
				return 0;
			if (o.getParentId() == this.getParentId())
				return 1;
			else
				return -1;
		}
		return -1;
	}

	public void setChildren(Set<TaxElement> children) {
		this.children = children;
	}

	public Set<TaxElement> getChildren() {
		return children;
	}

	public void setParent(TaxElement parent) {
		this.parent = parent;
	}

	public TaxElement getParent() {
		return parent;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TaxElement))
			return false;

		if (this.getId().equals(((TaxElement) obj).getId()))
			return true;

		return false;

	}

	@Override
	public int hashCode() {

		return getId();
	}

}
