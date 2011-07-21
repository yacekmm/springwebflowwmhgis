package pdm.tree.concept;

import java.io.Serializable;

import pdm.beans.TaxElement;
/** TODO dlaczego ta klasa nie rozszezrza taxElement tylko bez sensu wszystko kopiuje */
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
	/**
	 * 
	 */
	private Integer id;
	/**
	 * konstruktor
	 * @param _name nazwa obiektu
	 * @param _color kolor
	 * @param faceInHistoryHex kolor w postaci heksadecymalnej
	 * @param _abstractionIndex indeks abstrakcji
	 */
	public TaxElementInHistory(String _name, String _color, String faceInHistoryHex, int _abstractionIndex) {
		this.name = _name;
		this.color = _color;
		this.colorHex = faceInHistoryHex;
		this.abstractionIndex = _abstractionIndex;
		
	}
	
	/**
	 * konstruktor
	 * @param te TaxElement na podstawie ktorego zostanie zbudowany TaxElementInHistory
	 */
	public TaxElementInHistory(TaxElement te) {
		this.name = te.getData();
		this.color = te.getFace();
		this.colorHex = te.getFaceHex();
		this.abstractionIndex = te.getAbstractionIndex();
		this.id = te.getId();
	}
	
	/*
	 * zwraca nazwe obiektu
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * zwraca kolor w postaci heksadecymalnej
	 */
	public String getColorHex() {
		return colorHex;
	}
	
	/**
	 * zwraca kolor w postaci tekstowej (postac np. 'kolor-1')
	 * @return
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * ustawia indeks abstrakcji
	 * @param abstractionIndex indeks abstrakcji
	 */
	public void setAbstractionIndex(int abstractionIndex) {
		this.abstractionIndex = abstractionIndex;
	}
	
	/**
	 * pobiera indeks abstrakcji
	 * @return indeks abstrakcji
	 */
	public int getAbstractionIndex() {
		return abstractionIndex;
	}
/** 
 * Setter Id taksonomii
 * @param id
 */
	public void setId(Integer id) {
		this.id = id;
	}
/**
 * Getter Id taksonomii
 * @return
 */
	public Integer getId() {
		return id;
	}
/**
 * Typ - zielony, czerwony lub żaden(standard)
 * @return
 */
	public Boolean getType()
	{
		if (getColor().contains("green")) 
			return true;
		if (getColor().contains("red")) 
			return false;
			
		return null;
	}
}
