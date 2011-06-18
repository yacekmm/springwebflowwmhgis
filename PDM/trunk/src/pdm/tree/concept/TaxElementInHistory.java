package pdm.tree.concept;

import java.io.Serializable;

import pdm.beans.TaxElement;

public class TaxElementInHistory implements Serializable{
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = -873290635731200774L;
	/**
	 * nazwa TaxElementu (tekst)
	 */
	private String name;
	/**
	 * indeks abstrakcji przepisany z glownego konceptu
	 */
	private int abstractionIndex;
	/**
	 * kolor (tekstowo) taxElementu
	 */
	private String color;
	/**
	 * kolor (hexadecymalnie) taxElementu
	 */
	private String colorHex;
	
	public TaxElementInHistory(String _name, String _color, String faceInHistoryHex, int _abstractionIndex) {
		this.name = _name;
		this.color = _color;
		this.colorHex = faceInHistoryHex;
		this.abstractionIndex = _abstractionIndex;
	}
	public TaxElementInHistory(TaxElement te) {
		this.name = te.getData();
		this.color = te.getFace();
		this.colorHex = te.getFaceHex();
		this.abstractionIndex = te.getAbstractionIndex();
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
