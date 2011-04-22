package pdm.dao;

import hibernate.HibernateUtil;

import java.util.Vector;

import org.richfaces.model.TreeNodeImpl;

import pdm.beans.TaxElement;
import dao.DAO;

public class TaxElementDAO extends DAO<TaxElement> {

	private static final long serialVersionUID = -8287601353701236138L;

	@SuppressWarnings("unchecked")
	@Override
	public Vector<TaxElement> getObjects() {
		if (objects == null || objects.isEmpty()) {

			objects = new Vector<TaxElement>(HibernateUtil
					.getTable(TaxElement.class));
		}
		return objects;
	}
	
	public Vector<TreeNodeImpl<TaxElement>> getTreeObjects()
	{
		Vector<TreeNodeImpl<TaxElement>> treeObjects = new Vector<TreeNodeImpl<TaxElement>>();
		for (int i = 0; i< getObjects().size();i++)
		{
			TreeNodeImpl<TaxElement> tmp = new TreeNodeImpl<TaxElement>();
			getObjects().get(i).setTreeHolder(tmp);
			tmp.setData(getObjects().get(i));
			treeObjects.add(tmp);
			
		}
			
		return treeObjects;
	}
	

}
