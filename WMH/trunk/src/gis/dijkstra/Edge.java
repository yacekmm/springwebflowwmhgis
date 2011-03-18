package gis.dijkstra;

public interface Edge  {	
	public int getId();
	public Vertex getDestination();
	public Vertex getSource();
	public int getWeight();	
	public String toString(); 
	
}
