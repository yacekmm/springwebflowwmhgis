package pdm.Utils;

import java.util.ArrayList;

import java.util.List;

/**
 * klasa przechowuje dane o koorach stosowanych do kolorwania konceptow
 * @author jacek
 *
 */
public class ColorGradient {
	/*
	 * instancja statyczna tej klasy
	 */
	public static ColorGradient colorGradient;
	/**
	 * lista kolejnych gradientow koloru uzywanego do konceptow przedzialowych 
	 */
	private List<String> colorGradientNeutral, colorGradientRed, colorGradientGreen;

	/**
	 * kolor neutralny (edytowanego konceptu)
	 */
	private String neutralColor = Const.neutralColor;
	/**
	 * kolor konceptu wylaczonego z kwalifikatora
	 */
	private String excludedColor = Const.excludedColor;
	/**
	 * kolor konceptu wlaczonego do kwalifikatora
	 */
	private String includedColor = Const.includedColor;
	/**
	 * kolor standardowy elementow ktore nie sa w chwili obecnej prztwarzane 
	 */
	private String standardColor = Const.standardColor;
	
	/**
	 * konstruktor
	 */
	public ColorGradient(){
		//pobranie stalych
		Const constants = new Const();
		
		colorGradientNeutral = new ArrayList<String>();
		colorGradientRed = new ArrayList<String>();
		colorGradientGreen = new ArrayList<String>();
		
		colorGradientRed.add(Const.red0);
		colorGradientRed.add(Const.red1);
		colorGradientRed.add(Const.red2);
		colorGradientRed.add(Const.red3);
		colorGradientRed.add(Const.red4);
		colorGradientRed.add(Const.red5);
		
		colorGradientGreen.add(Const.green0);
		colorGradientGreen.add(Const.green1);
		colorGradientGreen.add(Const.green2);
		colorGradientGreen.add(Const.green3);
		colorGradientGreen.add(Const.green4);
		colorGradientGreen.add(Const.green5);
		
		colorGradientNeutral.add(constants.getOrange0());
		colorGradientNeutral.add(constants.getOrange1());
		colorGradientNeutral.add(constants.getOrange2());
		colorGradientNeutral.add(constants.getOrange3());
		colorGradientNeutral.add(constants.getOrange4());
		colorGradientNeutral.add(constants.getOrange5());
	}
	
	/**
	 * pobranie instancji klasy
	 * @return instancja klasy
	 */
	public static ColorGradient getInstance() {
		if (colorGradient == null) {
			colorGradient = new ColorGradient();
		}
		return colorGradient;
	}
	
	/**
	 * zwraca kolor
	 */
	public List<String> getColorGradientNeutral() {
		return colorGradientNeutral;
	}

	/**
	 * ustawia kolor
	 * @param colorGradientNeutral kolor
	 */
	public void setColorGradientNeutral(List<String> colorGradientNeutral) {
		this.colorGradientNeutral = colorGradientNeutral;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public List<String> getColorGradientRed() {
		return colorGradientRed;
	}

	/**
	 * ustawia kolor
	 * @param colorGradientRed kolor
	 */
	public void setColorGradientRed(List<String> colorGradientRed) {
		this.colorGradientRed = colorGradientRed;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public List<String> getColorGradientGreen() {
		return colorGradientGreen;
	}

	/**
	 * ustawia kolor
	 * @param colorGradientGreen kolor
	 */
	public void setColorGradientGreen(List<String> colorGradientGreen) {
		this.colorGradientGreen = colorGradientGreen;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public String getNeutralColor() {
		return neutralColor;
	}

	/**
	 * ustawia kolor
	 * @param neutralColor kolor
	 */
	public void setNeutralColor(String neutralColor) {
		this.neutralColor = neutralColor;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public String getExcludedColor() {
		return excludedColor;
	}

	/**
	 * ustawia kolor
	 * @param excludedColor kolor
	 */
	public void setExcludedColor(String excludedColor) {
		this.excludedColor = excludedColor;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public String getIncludedColor() {
		return includedColor;
	}

	/**
	 * ustawia kolor
	 * @param includedColor kolor
	 */
	public void setIncludedColor(String includedColor) {
		this.includedColor = includedColor;
	}

	/**
	 * zwraca kolor
	 * @return kolor
	 */
	public String getStandardColor() {
		return standardColor;
	}

	/**
	 * ustawia kolor
	 * @param standardColor kolor
	 */
	public void setStandardColor(String standardColor) {
		this.standardColor = standardColor;
	}
}
