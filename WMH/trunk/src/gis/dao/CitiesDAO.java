package gis.dao;

import gis.beans.City;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.Vector;
import org.hibernate.*;
/**
 * Class manages the cities list in the GIS project
 * @author pko
 *
 */
public class CitiesDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639419824413780694L;

	public CitiesDAO() {
	}

	private Vector<City> cities;
	private Object origin;
	private Object end;
	

	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}

	@SuppressWarnings("unchecked")
	public Vector<City> getCities() {
		if (cities == null || cities.isEmpty()) {
			cities = new Vector<City>(HibernateUtil.getTable(City.class));
		}
		return cities;
	}

	@SuppressWarnings("unused")
	private void insertCity() {
		Session session = HibernateUtil.sessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			City city = new City();
			// city.setId(373);
			city.setName("Warszawa");
			city.setLabel("ll");
			city.setLatitude(33);
			city.setLongitude(33);
			// session.saveOrUpdate(city);

			Object tmp = session.save(city);
			// newTodo.setUserId(user.getId());
			// newTodo.setId((Long)session.save(newTodo));
			// todos.add(newTodo);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public String getGeo() {
		if (cities == null) {
			getCities();
		}
		int tmp = cities.size();
		if( tmp > 10 ) tmp = 10;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tmp; i++) {
			sb
					.append("codeAddress(\"" + cities.elementAt(i).getName()
							+ "\");");
		}
		return sb.toString();

	}
	public String getGeo2() {
		if (cities == null) {
			getCities();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 10; i < cities.size(); i++) {
			sb
					.append("codeAddress(\"" + cities.elementAt(i).getName()
							+ "\");");
		}
		return sb.toString();

	}

	public String getRoute() {
		if (origin != null && end != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("calcRoute(\"" + origin + "\",\"" + end + "\");");
			return sb.toString();
		}
		return "";
	}

	public String getRouteWalking() {
		if (origin != null && end != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("calcRouteWalk(\"" + origin + "\",\"" + end + "\");");
			return sb.toString();
		}
		return "";
	}

	public void setOrigin(Object origin) {
		this.origin = origin;
	}

	public Object getOrigin() {
		return origin;
	}

	public void setEnd(Object end) {
		this.end = end;
	}

	public Object getEnd() {
		return end;
	}

	public City getOriginCity() {
		for (int i = 0; i < getCities().size(); i++) {
			if (getCities().get(i).equals(origin))
				return getCities().get(i);
		}
		return null;
	}

	public City getEndCity() {
		for (int i = 0; i < getCities().size(); i++) {
			if (getCities().get(i).equals(end))
				return getCities().get(i);
		}
		return null;
	}
	
	public  boolean getRenderRoute()
	{
		if (getOrigin()  == null || getEnd() == null)
		return false;
	 int oriID = this.getOriginCity().getId();
	 int endID = this.getEndCity().getId();
	// System.err.println("somethin");
	 if (oriID < 21 && endID < 21)
		 return true;
		
		return false;
	}
	

}
