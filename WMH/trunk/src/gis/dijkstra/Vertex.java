package gis.dijkstra;

public interface Vertex {

	public int getId();
	public String getName();	
	public int hashCode();	
	public boolean equals(Object obj);
	public String toString();
}
