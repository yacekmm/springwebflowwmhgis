package wmh.beans;

import java.util.List;
import java.util.Vector;

import gis.dijkstra.Edge;
import gis.dijkstra.Graph;
import gis.dijkstra.Vertex;
/**
 * Basic graph in the WMH project
 * @author pko
 *
 */
public class NetworkGraph implements Graph {

	private int id;
	private Vector<Connection> connections;
	private Vector<Switch> switches;
	private List<Edge> edges;
	private List<Vertex> vertexes;

	@Override
	public List<Edge> getEdges() {
		if (edges == null) {
			edges = new Vector<Edge>();
			if (connections != null) {
				for (int i = 0; i < connections.size(); i++) {
				//	if (connections.get(i).getStatus())
						edges.add(connections.get(i));
				}
			}
		}
		return edges;
	}

	public void setEdges(List<Edge> e) {
		edges = e;
	}

	@Override
	public List<Vertex> getVertexes() {
		if (vertexes == null) {
			vertexes = new Vector<Vertex>();
			if (switches != null) {
				for (int i = 0; i < switches.size(); i++)
					vertexes.add(switches.get(i));
			}
		}
		return vertexes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setConnections(Vector<Connection> connections) {
		this.connections = connections;
	}

	public Vector<Connection> getConnections() {
		if (connections == null)
			connections = new Vector<Connection>();
		return connections;
	}

	public void setSwitches(Vector<Switch> switches) {
		this.switches = switches;
	}

	public Vector<Switch> getSwitches() {
		if (switches == null)
			switches = new Vector<Switch>();
		return switches;
	}
	
	public int getSwitchesNumber()
	{
		if (switches == null)
			return 0;
		else return switches.size();
		
	}
	
	public int getConnectionsNumber()
	{
		if (connections == null)
			return 0;
		else return connections.size();
		
	}

}
