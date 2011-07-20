package pdm.tree;
/**
 * Wyliczanie używane przy kolorowaniu widoku(GUI)
 * @author pkonstanczuk
 *
 */
public enum Colors {
	ORANGE0("#FFD900"),ORANGE1("#FFDD33"),ORANGE2("#FFEE66"),ORANGE3("#FFEE99"),ORANGE4("#FFF7CC"),ORANGE5("#FFFBE6"),
	GREEN0("#00FF00"),GREEN1("#33FF33"), GREEN2("#66FF66"), GREEN3("#99FF99"), GREEN4("#CCFFCC"), GREEN5("#E6FFE6"),  
	RED0("#FF0000"),RED1("#FF3333"),RED2("#FF6666"),RED3("#FF9999"),RED4("#FFCCCC"),RED5("#FFE6E6");
/**
 * Przechowuje kolor w postaci String
 */
	private String c;
/**
 * Tworzy instancje wyliczenia z podanego String
 * @param c
 */
	Colors(String c) {
		this.c = c;
	}
/**
 * Zwraca kolor w postaci String
 * @return
 */
	public String getC() {
		
		return c;
	}

}
