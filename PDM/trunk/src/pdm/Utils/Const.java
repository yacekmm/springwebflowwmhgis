package pdm.Utils;

/**
 * Klasa przechowujaca stale funkcjonujace w aplikacji
 * @author pkonstanczuk
 *
 */
public class Const {
	
	
	

	/**
	 * Tekst ostrzezenia - Nie wybrano elementu z kazdej taksonomii!
	 */
	public static final String notAllSelected = "Nie wybrano elementu z każdej taksonomii!";
	/**
	 * Tekst ostrzezenia - Wybrano juz element z tej taksonomii!
	 */
	public static final String alreadySelected = "Wybrano już element z tej taksonomii!";
	/**
	 * Tekst informacji - Zapisano!
	 */
	public static final String success = "Zapisano!";
	/**
	 * Kazdy SearchRecord musi miec nazwe i opis!
	 */
	public static final String searchRecordNotCorrect = "Każdy rekord musi miec nazwe i opis!";
	
	/**
	 * tekst zachety do rozszerzenie konceptu o kolejne dziecko
	 */
	public static final String EXTENDER_PROMPT_TEXT = "Rozszerz koncept...";
	
	//walidacja w wyszukiwaniu:
	/**
	 * komunikat walidacji wariant 1
	 */
	public static final String VAL_MODE_1 = "Proba WŁĄCZENIA do kwalifikatora obiektu, ktorego RODZIC juz jest WŁĄCZONY";
	/**
	 * komunikat walidacji wariant 2
	 */
	public static final String VAL_MODE_2 = "Proba WYŁĄCZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest WYŁĄCZONY";
	/**
	 * komunikat walidacji wariant 3
	 */
	public static final String VAL_MODE_3 = "Proba WŁĄCZENIA do kwalifikatora obiektu, ktorego RODZIC jest WYŁĄCZONY";
	/**
	 * komunikat walidacji wariant 4
	 */
	public static final String VAL_MODE_4 = "Proba WYŁĄCZENIA z kwalifikatora obiektu, ktorego DZIECKO jest WŁĄCZONE";
	 /**
	 * komunikat walidacji wariant 5
	 */
	public static final String VAL_MODE_5 = "Proba WYŁĄCZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest WYŁĄCZONY";
	 /**
	 * komunikat walidacji wariant 6
	 */
	public static final String VAL_MODE_6 = "Nie można wykluczyć wszystkich konceptów (wybierz co najmniej jeden dołączony)";
	
	/**
	 * tekstowa nazwa koloru konceptu edytowanego
	 */
	public static final String neutralColor = "orange";
	/**
	 * tekstowa nazwa koloru konceptu WYLACZONEGO z kwalifikatora
	 */
	public static final String excludedColor = "red";
	/**
	 * tekstowa nazwa koloru konceptu WLACZONEGO do kwalifikatora
	 */
	public static final String includedColor = "green";
	/**
	 * tekstowa nazwa koloru konceptu nieuzywanego
	 */
	public static final String standardColor = "standard";
	
	/**
	 * gradient stopnia 0 koloru includedColor
	 */
	public static final String green0 = "#00FF00"; //"#00FF00";
	/**
	 * gradient stopnia 1 koloru includedColor
	 */
	public static final String green1 = "#9CFF9C"; //"#33FF33";
	/**
	 * gradient stopnia 2 koloru includedColor
	 */
	public static final String green2 = "#D1FFD1"; //"#66FF66";
	/**
	 * gradient stopnia 3 koloru includedColor
	 */
	public static final String green3 = "#E8FFE8"; //"#99FF99";
	/**
	 * gradient stopnia 4 koloru includedColor
	 */
	public static final String green4 = "#F3FFF3"; //"#CCFFCC";
	/**
	 * gradient stopnia 5 koloru includedColor
	 */
	public static final String green5 = "#F3FFF3"; //"#E6FFE6";

	
	/**
	 * gradient stopnia 0 koloru excludedColor
	 */
	public static final String red0 = "#FF0000";
	/**
	 * gradient stopnia 1 koloru excludedColor
	 */
	public static final String red1 = "#FF3333";
	/**
	 * gradient stopnia 2 koloru excludedColor
	 */
	public static final String red2 = "#FF6666";
	/**
	 * gradient stopnia 3 koloru excludedColor
	 */
	public static final String red3 = "#FF9999";
	/**
	 * gradient stopnia 4 koloru excludedColor
	 */
	public static final String red4 = "#FFCCCC";
	/**
	 * gradient stopnia 5 koloru excludedColor
	 */
	public static final String red5 = "#FFE6E6";

	/**
	 * gradient stopnia 0 koloru neutralColor
	 */
	private String orange0 = "#ffd500"; //"#FFD900";
	/**
	 * gradient stopnia 1 koloru neutralColor
	 */
	private String orange1 = "#FFE873"; //"#FFDD33";
	/**
	 * gradient stopnia 2 koloru neutralColor
	 */
	private String orange2 = "#FFF1AC"; //"#FFE666";
	/**
	 * gradient stopnia 3 koloru neutralColor
	 */
	private String orange3 = "#FFF8D5"; //"#FFEE99";
	/**
	 * gradient stopnia 4 koloru neutralColor
	 */
	private String orange4 = "#FFFBEA"; //"#FFF7CC";
	/**
	 * gradient stopnia 5 koloru neutralColor
	 */
	private String orange5 = "#FFFBEA"; //"#FFFBE6";
	
	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange0() { return orange0; }
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange0(String orange0) {}

	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange1() {return orange1;}
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange1(String orange1) {}

	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange2() {return orange2;}
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange2(String orange2) {}

	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange3() {return orange3;	}
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange3(String orange3) {}

	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange4() {return orange4;	}
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange4(String orange4) {}

	/**
	 * pobierz gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public String getOrange5() {return orange5;	}
	/**
	 * ustaw gradient koloru neutralnego
	 * @return gradient koloru neutralnego
	 */
	public void setOrange5(String orange5) {}
}