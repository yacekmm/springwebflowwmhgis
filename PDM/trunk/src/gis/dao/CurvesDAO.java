package gis.dao;
import gis.beans.City;

import gis.beans.Curve;
import gis.beans.Curve.Type;
import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * Class manages roads in the GIS project
 * @author pko
 *
 */
public class CurvesDAO implements Serializable {

	private static final long serialVersionUID = 5639419824413780694L;
	private Vector<Curve> curves = null;	
	public void setCurves(Vector<Curve> c) {
		this.curves = c;
	}
	@SuppressWarnings("unchecked")
	public Vector<Curve> getCurves() {
		//insertCurve();
				
		if (curves == null || curves.isEmpty()) {
			curves = new Vector<Curve>( HibernateUtil.getTable(Curve.class));
		
		}
		return curves;
	}
	
	void insertCurve()
	{ try
	{
	Curve tmp = new Curve();
	City from = new City();
	from.setName("Warszawa");
	from.setLongitude(33);
	from.setLatitude(33);
	from.setLabel("ll");
	City to  = new City();
	to.setName("Radom");
	to.setLongitude(33);
	to.setLatitude(33);
	to.setLabel("ll");
	
	tmp.setSource(from);
	tmp.setDestination(to);
	tmp.setType(Type.DRIVING);
	tmp.setWeight(20);
	tmp.setId(1);
	Session session = HibernateUtil.getSession();
	Transaction tr = session.beginTransaction();
	session.save(from);
	session.save(to);
	session.save(tmp);
	tr.commit();
	}catch (Exception e)
	{
		e.printStackTrace();
	}
	
	//session.close();
	
	}
}
	
	