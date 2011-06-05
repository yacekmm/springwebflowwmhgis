package pdm.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColorGradient {
	public static ColorGradient colorGradient;
	private List<String> colorGradientNeutral, colorGradientRed, colorGradientGreen;

	private String neutralColor = Const.neutralColor;
	private String excludedColor = Const.excludedColor;
	private String includedColor = Const.includedColor;
	private String standardColor = Const.standardColor;
	
	
	private HashMap<Integer, String> orangeMap;
	
	public ColorGradient(){
		Const constants = new Const();
		
		orangeMap = new HashMap<Integer, String>();
		orangeMap.put(0, constants.getOrange0());
		orangeMap.put(1, constants.getOrange1());
		orangeMap.put(2, constants.getOrange2());
		orangeMap.put(3, constants.getOrange3());
		orangeMap.put(4, constants.getOrange4());
		orangeMap.put(5, constants.getOrange5());
		
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
	
	public static ColorGradient getInstance() {
		if (colorGradient == null) {
			colorGradient = new ColorGradient();
		}
		return colorGradient;
	}
	
	public List<String> getColorGradientNeutral() {
		return colorGradientNeutral;
	}

	public void setColorGradientNeutral(List<String> colorGradientNeutral) {
		this.colorGradientNeutral = colorGradientNeutral;
	}

	public List<String> getColorGradientRed() {
		return colorGradientRed;
	}

	public void setColorGradientRed(List<String> colorGradientRed) {
		this.colorGradientRed = colorGradientRed;
	}

	public List<String> getColorGradientGreen() {
		return colorGradientGreen;
	}

	public void setColorGradientGreen(List<String> colorGradientGreen) {
		this.colorGradientGreen = colorGradientGreen;
	}

	public String getNeutralColor() {
		return neutralColor;
	}

	public void setNeutralColor(String neutralColor) {
		this.neutralColor = neutralColor;
	}

	public String getExcludedColor() {
		return excludedColor;
	}

	public void setExcludedColor(String excludedColor) {
		this.excludedColor = excludedColor;
	}

	public String getIncludedColor() {
		return includedColor;
	}

	public void setIncludedColor(String includedColor) {
		this.includedColor = includedColor;
	}

	public String getStandardColor() {
		return standardColor;
	}

	public void setStandardColor(String standardColor) {
		this.standardColor = standardColor;
	}

	public HashMap<Integer, String> getOrangeMap() {
		return orangeMap;
	}

	public void setOrangeMap(HashMap<Integer, String> orangeMap) {
		this.orangeMap = orangeMap;
	}
}
