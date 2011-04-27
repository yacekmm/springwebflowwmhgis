package pdm.Utils;

import java.util.ArrayList;
import java.util.List;

public class ColorGradient {
	public static ColorGradient colorGradient;
	public List<String> colorGradientNeutral, colorGradientRed, colorGradientGreen;

	public String neutralColor = "orange";
	public String excludedColor = "red";
	public String includedColor = "green";
	
	public ColorGradient(){
		colorGradientNeutral = new ArrayList<String>();
		colorGradientRed = new ArrayList<String>();
		colorGradientGreen = new ArrayList<String>();
		
		colorGradientRed.add("#FF0000");
		colorGradientRed.add("#FF3333");
		colorGradientRed.add("#FF6666");
		colorGradientRed.add("#FF9999");
		colorGradientRed.add("#FFCCCC");
		colorGradientRed.add("#FFE6E6");
		
		colorGradientGreen.add("#00FF00");
		colorGradientGreen.add("#33FF33");
		colorGradientGreen.add("#66FF66");
		colorGradientGreen.add("#99FF99");
		colorGradientGreen.add("#CCFFCC");
		colorGradientGreen.add("#E6FFE6");
		
		colorGradientNeutral.add("#FFD900");
		colorGradientNeutral.add("#FFDD33");
		colorGradientNeutral.add("#FFE666");
		colorGradientNeutral.add("#FFEE99");
		colorGradientNeutral.add("#FFF7CC");
		colorGradientNeutral.add("#FFFBE6");
	}
	
	public static ColorGradient getInstance() {
		if (colorGradient == null) {
			colorGradient = new ColorGradient();
		}
		return colorGradient;
	}
}
