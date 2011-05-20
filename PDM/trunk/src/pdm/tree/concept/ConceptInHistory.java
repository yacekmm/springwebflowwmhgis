package pdm.tree.concept;

import java.io.Serializable;

public class ConceptInHistory implements Serializable{
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = -873290635731200774L;
	private String name;
	private String color;
	private String colorHex;
	
	public ConceptInHistory(String _name, String _color, String faceInHistoryHex) {
		this.name = _name;
		this.color = _color;
		this.colorHex = faceInHistoryHex;
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
}
