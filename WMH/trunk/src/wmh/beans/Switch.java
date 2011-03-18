package wmh.beans;

import gis.dijkstra.Vertex;
/**
 * Basix switch in the WMH project - implements Vertex
 * @author pko
 *
 */
public class Switch implements Vertex {

	private int id;
	private String name;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null)
			if (obj instanceof Vertex) {
				Vertex tmp = (Vertex) obj;
				if (tmp.getId() == this.getId() && tmp.getName() == this.getName())
					return true;
			}
		return false;
	}



}
