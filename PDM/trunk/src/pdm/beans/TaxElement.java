package pdm.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import dao.Id;

public class TaxElement /* extends TreeNodeImpl<String> */implements Id,
		Serializable {

	private static final long serialVersionUID = 6913477020202058253L;
	private Integer Id;
	private Integer ParentId;
	private String data;
	private TreeNodeImpl<TaxElement> treeHolder;
	private String trace;
	private String face;

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
		this.face = face;
		for (Iterator<Entry<Object, TreeNode<TaxElement>>> i = treeHolder
				.getChildren(); i.hasNext();) {
			i.next().getValue().getData().setFace(face);
		}

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

}
