package gis.beans;

import java.io.Serializable;
/**
 * Basic class form managing Google MAP in GIS 
 * @author pko
 *
 */

public class GMapBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8293972201122480112L;
	private int Zoom = 6;
	private  String GMapKey = "dd";
	private double latitude = 52.368892, longitude = 19.116211;
	private String mapType = "google.maps.MapTypeId.ROADMAP";
	private String city = "Warszawa";
	//private String geo;
	public int getZoom() {
		return Zoom;
	}
	public void setZoom(int zoom) {
		Zoom = zoom;
	}
	public String getGMapKey() {
		return GMapKey;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public String getMapType() {
		return mapType;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	

	//public void setGeo(String geo) {
	//	//this.geo = geo;
	//}
	public String getGeo() {
		return "codeAddress(\"Kraków\");";
	}
	
	

	

	

}
