package pdm.beans;

import java.io.Serializable;

import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.ColorGradient;
import pdm.Utils.PdmLog;
import dao.Id;

public class TaxElement implements Id, Serializable {

	private static final long serialVersionUID = 6913477020202058253L;
	private Integer Id;
	private Integer ParentId;
	private String data;
	private TreeNodeImpl<TaxElement> treeHolder;
	private String trace;
	/**
	 * tekstowa nazwa stylu ktory bedzie uzywany do wyboru koloru dla prezentacji elementu w widoku (np. orange-0, red-3)
	 */
	private String face;
	/**
	 * Hexadecymalny odpowiednik tego co jest zapisane w face. zdefiniowane na podstawie danych statycznych z klasy ColorGradient
	 */
	private String faceHex;
//	/**
//	 * kolor ktory bedzie prezentowany w historii wybranych konceptow w widoku, ale w przeciwienstwie do face, 
//	 * nie bedzie kolorowany na drzewie taksonomii
//	 */
//	private String faceInHistory;
//	/**
//	 * Hexadecymalny odpowiednik tego co jest zapisane w faceInHistory. zdefiniowane na podstawie danych statycznych z klasy ColorGradient
//	 */
//	private String faceInHistoryHex;

	public void setTrace(String tr) {
		this.trace = tr;
	}

	public String getTrace() {
		try {
			StringBuilder sb = new StringBuilder();
			if (this.data != null) {
				sb.append(this.data);
			}
			// Object o = this;
			if (treeHolder.getParent() != null) {
				if (treeHolder.getParent().getData() != null) {
					sb.insert(0,
						treeHolder.getParent().getData().getTrace() + '.');
				}
			}
			trace = sb.toString();
			return trace;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public String getFace() {
		if (face == null)
			face = ColorGradient.getInstance().standardColor;

		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@Override
	public Integer getId() {
		return Id;
	}

	@Override
	public void setId(Integer id) {
		Id = id;
	}

	public void setParentId(Integer parentId) {
		ParentId = parentId;
	}

	public Integer getParentId() {
		return ParentId;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setTreeHolder(TreeNodeImpl<TaxElement> treeHolder) {
		this.treeHolder = treeHolder;
	}

	public TreeNodeImpl<TaxElement> getTreeHolder() {
		return treeHolder;
	}

	public void setFaceHex(String faceHex) {
		this.faceHex = faceHex;
	}

	public String getFaceHex() {
		faceHex =  mapTextFaceToHex(face);
		
		return faceHex;
	}

	private String mapTextFaceToHex(String textFace) {
		String hexResult = ""; 
		if(textFace==null){
			PdmLog.getLogger().error("face bya nullem. ustawiam domyslnie na standardowa");
			textFace = ColorGradient.getInstance().standardColor;
		}
		
		if(textFace.contains("-")){
			int gradientValue = Integer.parseInt(textFace.substring(textFace.indexOf("-")+1, textFace.length()));
			String color = textFace.substring(0, textFace.indexOf("-"));
			
			if(color.equalsIgnoreCase(ColorGradient.getInstance().includedColor)) {
				hexResult = ColorGradient.colorGradient.colorGradientGreen.get(gradientValue);
			}else if(color.equalsIgnoreCase(ColorGradient.getInstance().excludedColor)){
				hexResult = ColorGradient.colorGradient.colorGradientRed.get(gradientValue);
			}
		}else{
			PdmLog.getLogger().warn("Problem z prztlumaczeniem koloru TaxElementu na wartosc typu HEX");
			hexResult = ColorGradient.getInstance().standardColor;
		}
		
		return hexResult;
	}

	 @Override public String toString() {
		return this.data; 
	 }

//	public void setFaceInHistoryHex(String faceInHistoryHex) {
//		this.faceInHistoryHex = faceInHistoryHex;
//	}
//
//	public String getFaceInHistoryHex() {
//		faceInHistoryHex = mapTextFaceToHex(faceInHistory);
//		
//		return faceInHistoryHex;
//	}
//
//	public void setFaceInHistory(String faceInHistory) {
//		this.faceInHistory = faceInHistory;
//	}
//
//	public String getFaceInHistory() {
//		if(faceInHistory==null)
//			faceInHistory = ColorGradient.getInstance().standardColor;
//		return faceInHistory;
//	}
}
