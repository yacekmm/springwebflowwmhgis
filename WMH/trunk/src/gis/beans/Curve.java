package gis.beans;

import gis.dijkstra.Edge;

/**
 * Representation of road in the GIS
 * @author pko
 *
 */
public class Curve implements Edge {
	
	public enum Type {
	    DRIVING, WALKING
	}	
	
	private int id;
	private City source;
	private City destination;
	private int weight;
	private Type type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public City getSource() {
		return source;
	}
	public void setSource(City from) {
		this.source = from;
	}
	public City getDestination() {
		return destination;
	}
	public void setDestination(City to) {
		this.destination = to;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int value) {
		this.weight = value;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override 
	public String toString()
	{
		return source + " " + destination;	
	}
}
