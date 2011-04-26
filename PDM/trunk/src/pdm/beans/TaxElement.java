package pdm.beans;

import java.io.Serializable;

import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.ColorGradient;
import dao.Id;

public class TaxElement /* extends TreeNodeImpl<String> */implements Id,
		Serializable {

	private static final long serialVersionUID = 6913477020202058253L;
	private Integer Id;
	private Integer ParentId;
	private String data;
	private TreeNodeImpl<TaxElement> treeHolder;
	private String trace;
	private String face, faceHex;
	private boolean faceLocked = false;

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
			face = "standard";

		return face;
	}

	public void setFace(String face) {
		//System.out.println("Setting face from '" + this.face + "' to '" + face + "' for '" + data + "', locked = " + faceLocked);
		this.face = face;
	
//		for (Iterator<Entry<Object, TreeNode<TaxElement>>> i = treeHolder.getChildren(); i.hasNext();) {
//				i.next().getValue().getData().setFace(face);
//		}
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

	public void setFaceLocked(boolean faceLocked) {
		this.faceLocked = faceLocked;
	}

	public boolean isFaceLocked() {
		return faceLocked;
	}

	public void setFaceHex(String faceHex) {
		this.faceHex = faceHex;
	}

	public String getFaceHex() {
		if(face.contains("-")){
			ColorGradient.getInstance();
			int gradientValue = Integer.parseInt(face.substring(face.indexOf("-")+1, face.length()));
			String color = face.substring(0, face.indexOf("-"));
			
			if(color.equalsIgnoreCase("green")) {
				faceHex = ColorGradient.colorGradientGreen.get(gradientValue);
			}else if(color.equalsIgnoreCase("red")){
				faceHex = ColorGradient.colorGradientRed.get(gradientValue);
			}
		}
		
		return faceHex;
	}

}
