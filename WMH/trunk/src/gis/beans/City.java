package gis.beans;

import gis.dijkstra.Vertex;

import javax.faces.model.SelectItem;

/**
 * Representation of city for GIS
 * @author pko
 *
 */
public class City extends SelectItem implements Vertex{

	private int id;
	
	@Override
	public String getLabel() {
		return Name;	}

	@Override
	public Object getValue() {
		return this;
	}


	private static final long serialVersionUID = 3844458898217170194L;
	private double latitude = 52.368892;
	private double longitude = 19.116211;
	private String Name = "empty";

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return Name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		return obj.toString().equals(this.toString());
		
	}
	
	
	
	

}
