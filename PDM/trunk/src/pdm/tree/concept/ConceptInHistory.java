package pdm.tree.concept;

import java.io.Serializable;

public class ConceptInHistory implements Serializable{
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = -873290635731200774L;
	private String name;
	private int abstractionIndex;
	private String color;
	private String colorHex;
	
	public ConceptInHistory(String _name, String _color, String faceInHistoryHex, int _abstractionIndex) {
		this.name = _name;
		this.color = _color;
		this.colorHex = faceInHistoryHex;
		this.abstractionIndex = _abstractionIndex;
	}
	public String getName() {
		return name;
	}
	public String getColorHex() {
		return colorHex;
	}
	public String getColor() {
		return color;
	}
	public void setAbstractionIndex(int abstractionIndex) {
		this.abstractionIndex = abstractionIndex;
	}
	public int getAbstractionIndex() {
		return abstractionIndex;
	}
}
