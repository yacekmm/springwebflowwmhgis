package pdm.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import pdm.beans.TaxElement;

public class TreeBeanHelper {
	List<TaxElement> list;
	
	public TreeBeanHelper()
	{
		//list = new ArrayList<TaxElement>();
	}
	
	public List<TaxElement> allChildren(TaxElement t)
	{
		list = new ArrayList<TaxElement>();
		list.add(t);
		allChildrenLoop(t);
		
		
		return list;
	}
	
	private void allChildrenLoop(TaxElement t)
	{
		TaxElement tmp;
		for (  Iterator<TaxElement> it = t.getChildren().iterator(); it.hasNext();)
		{
			tmp = it.next();
			list.add(tmp);
			allChildrenLoop(tmp);
			
			
		}
	}

}
