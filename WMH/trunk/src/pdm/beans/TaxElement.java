package pdm.beans;

import org.richfaces.model.TreeNodeImpl;


import java.io.Serializable;
import dao.Id;

public class TaxElement extends TreeNodeImpl<String> implements Id,
		Serializable {

	private static final long serialVersionUID = 6913477020202058253L;
	private Integer Id;
	private Integer ParentId;
	private String Data;
	private String Face;
	
	public TaxElement()
	{
		setData("new");
	}
	
	public String getFace()
	{
		
		return ParentId >0 ? "chi" : "par";
	}
	
	public void setFace(String face)
	{
		Face = face;
	}


	@Override
	public String getData() {
		return Data;
	}

	@Override
	public void setData(String data) {
		Data = data;
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



}
