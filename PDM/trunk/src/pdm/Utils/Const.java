package pdm.Utils;

public class Const {

	public static final String notAllSelected = "Nie wybrano elementu z każdej taksonomii!";
	public static final String alreadySelected = "Wybrano już element z tej taksonomii!";
	public static final String success = "Zapisano!";
	public static final String searchRecordNotCorrect = "Każdy SearchRecord musi mieć nazwę i opis!";
	
	public static final String EXTENDER_PROMPT_TEXT = "Rozszerz koncept...";
	
	//walidacja w wyszukiwaniu:
	public static final String VAL_MODE_1 = "Proba WŁĄCZENIA do kwalifikatora obiektu, którego RODZIC juz jest WŁĄCZONY";
	public static final String VAL_MODE_2 = "Próba WYŁĄCZENIA z kwalifikatora obiektu, którego RODZIC już jest WYŁĄCZONY";
	public static final String VAL_MODE_3 = "Próba WŁĄCZENIA do kwalifikatora obiektu, którego RODZIC jest WYŁĄCZONY"; 
	public static final String VAL_MODE_4 = "Próba WYŁĄCZENIA z kwalifikatora obiektu, którego DZIECKO jest WŁĄCZONE";
	
	public static final String neutralColor = "orange";
	public static final String excludedColor = "red";
	public static final String includedColor = "green";
	public static final String standardColor = "standard";
	
	public static final String green0 = "#00FF00";
	public static final String green1 = "#33FF33";
	public static final String green2 = "#66FF66";
	public static final String green3 = "#99FF99";
	public static final String green4 = "#CCFFCC";
	public static final String green5 = "#E6FFE6";
	
	public static final String red0 = "#FF0000";
	public static final String red1 = "#FF3333";
	public static final String red2 = "#FF6666";
	public static final String red3 = "#FF9999";
	public static final String red4 = "#FFCCCC";
	public static final String red5 = "#FFE6E6";

	private String orange0 = "#FFD900";
	private String orange1 = "#FFDD33";
	private String orange2 = "#FFE666";
	private String orange3 = "#FFEE99";
	private String orange4 = "#FFF7CC";
	private String orange5 = "#FFFBE6";


	public String getOrange0() { return orange0; }
	public void setOrange0(String orange0) {}

	public String getOrange1() {return orange1;}
	public void setOrange1(String orange1) {}

	public String getOrange2() {return orange2;}
	public void setOrange2(String orange2) {}

	public String getOrange3() {return orange3;	}
	public void setOrange3(String orange3) {}

	public String getOrange4() {return orange4;	}
	public void setOrange4(String orange4) {}

	public String getOrange5() {return orange5;	}
	public void setOrange5(String orange5) {}
}