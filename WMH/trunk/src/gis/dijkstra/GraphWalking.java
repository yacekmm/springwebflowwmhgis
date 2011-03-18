package gis.dijkstra;

import gis.beans.Curve;
import gis.beans.Curve.Type;
import java.util.List;
import java.util.Vector;

public class GraphWalking extends GraphDriving {

	public List<Edge> getEdges() {
		if (edges == null || edges.isEmpty()) {
			edges = new Vector<Edge>();
			for (int i = 0; i < getCurvesDAO().getCurves().size(); i++) {
				if (getCurvesDAO().getCurves().get(i).getType() == Type.WALKING) {
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

}
