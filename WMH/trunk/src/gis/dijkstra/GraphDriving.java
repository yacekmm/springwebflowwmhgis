package gis.dijkstra;

import gis.beans.Curve;
import gis.beans.Curve.Type;
import gis.dao.CitiesDAO;
import gis.dao.CurvesDAO;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class GraphDriving  implements Graph{

	protected CitiesDAO citiesDAO;
	protected CurvesDAO curvesDAO;
	Vector<Vertex> vertexes;
	Vector<Edge> edges;
	DijkstraAlgorithm da;

	public List<Vertex> getVertexes() {
		if (vertexes == null || vertexes.isEmpty()) {
			vertexes = new Vector<Vertex>();
			for (int i = 0; i < citiesDAO.getCities().size(); i++) {
				vertexes.add(citiesDAO.getCities().get(i));
			}

		}
		return vertexes;
	}

	public List<Edge> getEdges() {
		if (edges == null || edges.isEmpty()) {
			edges = new Vector<Edge>();
			for (int i = 0; i < curvesDAO.getCurves().size(); i++) {
				if (curvesDAO.getCurves().get(i).getType() == Type.DRIVING)
				{
				Curve tmp = curvesDAO.getCurves().get(i);
				Curve tmp2 = new Curve();
				tmp2.setSource(tmp.getDestination());
				tmp2.setDestination(tmp.getSource());
				tmp2.setWeight(tmp.getWeight());
				tmp2.setType(tmp.getType());
				edges.add(tmp);
				edges.add(tmp2);
				}
			}

		}
		return edges;
	}

	public void setCitiesDAO(CitiesDAO citiesDAO) {
		this.citiesDAO = citiesDAO;
	}

	public CitiesDAO getCitiesDAO() {
		return citiesDAO;
	}

	public void setCurvesDAO(CurvesDAO curvesDAO) {
		this.curvesDAO = curvesDAO;
	}

	public CurvesDAO getCurvesDAO() {
		return curvesDAO;
	}
	public Integer getPathValue()
	{
		if (citiesDAO.getOriginCity() != null && citiesDAO.getEndCity() != null)
		{
			
		 return da.getShortestDistance((Vertex) citiesDAO.getEndCity());
		}
		return 0;
	}

	public List<Vertex> getPath() {
		LinkedList<Vertex> list = null;
		Long tmp = 0L;
		if (citiesDAO.getOriginCity() != null && citiesDAO.getEndCity() != null) {
			tmp = Calendar.getInstance().getTimeInMillis();
			da = new DijkstraAlgorithm(this);
			da.execute((Vertex) citiesDAO.getOriginCity());
			list = da.getPath((Vertex) citiesDAO
					.getEndCity());
			
			tmp = Calendar.getInstance().getTimeInMillis() - tmp;
			System.err.println("Czas wykonania: " + tmp);
		}
		
		return list ;

	}
	
	public Integer getPathTime()
	{
		if (citiesDAO.getOriginCity() != null && citiesDAO.getEndCity() != null)
		{
			return da.getShortestDistance((Vertex) citiesDAO.getEndCity()) / 90;
		// return da.getShortestDistance((Vertex) citiesDAO.getEndCity());
		}
		return 0;
	}

}
