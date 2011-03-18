package wmh.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import gis.dijkstra.DijkstraAlgorithm;
import wmh.beans.Connection;
import wmh.beans.GraphResults;
import wmh.beans.NetworkGraph;
import wmh.beans.ProgressBarBean;
import wmh.beans.Switch;

/**
 * Main class responsible for Monte Carlo Algorithm
 * 
 * @author pko
 * 
 */
public class MonteCarlo {

	// liczba vertexow symulowanego grafu
	private Integer vertexNumber = 2;

	// spojnosc grafu
	private Integer complexity = 100;
	// prawdopodobienstwo bezawaryjnej pracy
	private Integer noErrorRate = 100;
	// graf testowy
	private NetworkGraph testGraph;
	// N liczba prob
	private Integer nTrialsCounter;
	private Integer trustParam = 95;
	private Integer errorAppr = 5;

	//private final int minTrialsParam = 10;
	private final int maxTrialsParam = 10000;
	private final String logger = "monteCarlo";

	private SwitchesDAO switchesDAO;
	private ConnectionsDAO connectionsDAO;
	private GraphResultsDAO graphResultsDAO;
	private NetworkGraphsDAO networkGraphsDAO;

	private ProgressBarBean progressBarBean = new ProgressBarBean();

	private ArrayList<String> neighbours;

	/**
	 * returns list of switches
	 * 
	 * @return list of switches
	 */

	public SwitchesDAO getSwitchesDAO() {
		return switchesDAO;
	}

	/**
	 * sets list of switches
	 * 
	 * @param switchesDAO
	 */
	public void setSwitchesDAO(SwitchesDAO switchesDAO) {
		this.switchesDAO = switchesDAO;

	}

	/**
	 * returns list of connections
	 * 
	 * @return list of connections
	 */
	public ConnectionsDAO getConnectionsDAO() {
		return connectionsDAO;
	}

	/**
	 * sets list of connections
	 * 
	 * @param connectionsDAO
	 */
	public void setConnectionsDAO(ConnectionsDAO connectionsDAO) {
		this.connectionsDAO = connectionsDAO;
	}

	/**
	 * returns DAO for GraphResults
	 * 
	 * @return
	 */
	public GraphResultsDAO getGraphResultsDAO() {
		return graphResultsDAO;
	}

	/**
	 * sets DAO for Graph results
	 * 
	 * @param graphResultsDAO
	 */
	public void setGraphResultsDAO(GraphResultsDAO graphResultsDAO) {
		this.graphResultsDAO = graphResultsDAO;
	}

	/**
	 * gets DAO for networkGraphs
	 * 
	 * @return
	 */
	public NetworkGraphsDAO getNetworkGraphsDAO() {
		return networkGraphsDAO;
	}

	/**
	 * sets DAO for NetworkGraphs
	 * 
	 * @param networkGraphsDAO
	 */
	public void setNetworkGraphsDAO(NetworkGraphsDAO networkGraphsDAO) {
		this.networkGraphsDAO = networkGraphsDAO;
	}

	/**
	 * sets Vertex amount
	 * 
	 * @param vertexNumber
	 */
	public void setVertexNumber(Integer vertexNumber) {
		if (vertexNumber < 0)
			this.vertexNumber = 0;
		this.vertexNumber = vertexNumber;
	}

	/**
	 * returns vertex amount
	 * 
	 * @return
	 */
	public Integer getVertexNumber() {
		if (vertexNumber == null)
			vertexNumber = 0;
		return vertexNumber;
	}

	/**
	 * sets complexity
	 * 
	 * @param complexity
	 */
	public void setComplexity(Integer complexity) {
		if (complexity < 0)
			this.complexity = 0;
		if (complexity > 100)
			this.complexity = 100;
		this.complexity = complexity;
	}

	/**
	 * returns complexity
	 * 
	 * @return
	 */
	public Integer getComplexity() {
		if (complexity == null)
			complexity = 100;
		return complexity;
	}

	/**
	 * sets no errorrate
	 * 
	 * @param errorRate
	 */
	public void setNoErrorRate(Integer errorRate) {
		if (errorRate > 100)
			this.noErrorRate = 100;
		if (errorRate < 0)
			this.noErrorRate = 0;
		this.noErrorRate = errorRate;
	}

	/**
	 * returns no error rate
	 * 
	 * @return
	 */

	public Integer getNoErrorRate() {
		if (noErrorRate == null)
			noErrorRate = 0;
		return noErrorRate;
	}

	/**
	 * generates test graph
	 * 
	 * @return
	 */

	public boolean generateTestGraph() {
		// 10 prob generowania grafow o zadanych parametrach
		if (testGraph == null) {
			Logger.getLogger(logger).info("Generating test graph");
			if (getComplexity() > 0 && getComplexity() < 101
					&& getVertexNumber() > 2) {
				int i = 1;
				progressBarBean.startProcess();

				while (i < 11) {
					progressBarBean.setCurrentValue(Long.valueOf(i * 10));

					testGraph = generateTestGraphWithoutChecking();
					if (checkIntegrity(testGraph)) {
						progressBarBean.setComplete();
						return true;
					}
					i--;

				}
			}
			testGraph = null;
			progressBarBean.setComplete();
			Logger.getLogger(logger).warning("graph not created");
			return false;

		}
		progressBarBean.setEnabled(false);
		return true;
	}

	/**
	 * genrates testgraph without initial checking
	 * 
	 * @return
	 */
	private NetworkGraph generateTestGraphWithoutChecking() {
		int counter = 1;
		Connection con;
		Switch sh1;
		NetworkGraph fullGraph = new NetworkGraph();
		for (int i = 0; i < vertexNumber; i++) {
			sh1 = new Switch();
			sh1.setName("sh" + counter);
			sh1.setId(counter);
			fullGraph.getSwitches().add(sh1);
			counter++;
		}
		for (int i = 0; i < vertexNumber; i++) {
			for (int i2 = i + 1; i2 < vertexNumber; i2++) {

				if (Const.check(complexity)) {
					con = new Connection();
					con.setSource(fullGraph.getSwitches().get(i));
					con.setDestination(fullGraph.getSwitches().get(i2));
					con.setWeight(complexity);
					fullGraph.getConnections().add(con);
				}
			}
		}
		return fullGraph;
	}

	/**
	 * checks integrity of graph
	 * 
	 * @param g
	 * @return
	 */
	private boolean checkIntegrity(NetworkGraph g) {
		if (g != null) {
			DijkstraAlgorithm da = new DijkstraAlgorithm(g);
			da.execute(g.getVertexes().get(0));
			for (int i2 = 0; i2 < g.getVertexes().size(); i2++) {
				if (da.getShortestDistance(g.getVertexes().get(i2)) == Integer.MAX_VALUE) {
					// nie istnieje sciezka do ktoregos z vertexow
					return false;
				}
			}
			// istnieje sciezka do kazdego z vertexow
			return true;

		}
		// graf jest nullem
		return false;
	}

	/**
	 * creates one graph for monte carlo method
	 * 
	 * @return
	 */
	private NetworkGraph createStepGraph() {

		NetworkGraph stepGraph = new NetworkGraph();
		Iterator<Switch> itrS = testGraph.getSwitches().iterator();
		while (itrS.hasNext()) {
			stepGraph.getSwitches().add(itrS.next());
		}

		for (int i = 0; i < testGraph.getConnections().size(); i++) {
			if (Const.check(noErrorRate)) {
				stepGraph.getConnections().add(
						testGraph.getConnections().get(i));
			}

		}
		/*
		 * System.err.println("PIERStepGraph connections: "
		 * +stepGraph.getConnections().size());
		 * System.err.println("testGraph connections: "
		 * +testGraph.getConnections().size());
		 * System.err.println("PIERStepGraph switches: "
		 * +stepGraph.getSwitches().size());
		 * System.err.println("testGraph switches: "
		 * +testGraph.getSwitches().size());
		 */
		return stepGraph;
	}

	/**
	 * initiates log
	 */
	public void initLog() {
		Logger log = Logger.getLogger(logger);
		ConsoleHandler ch = new ConsoleHandler();

		log.addHandler(ch);

	}

	private Integer trialsCounter() {
		double erro = ((double) (errorAppr)) / 100;
		double errorsq = erro * erro;
		double trust = 1 - (((double) (trustParam)) / 100);
		double tmp = 1 / (errorsq * trust);
		double tmp2 = 0;
		nTrialsCounter = (int) Math.ceil(tmp);
		
		if (nTrialsCounter > maxTrialsParam)
		{
			nTrialsCounter = maxTrialsParam;
			trustParam =  (int)((1 - ( 1/(((double)(maxTrialsParam))*errorsq)))*100);
			
		}
		System.err.println(tmp2);
		return nTrialsCounter;
	}

	/**
	 * main method - monteCarlo algorithm
	 */
	public void monteCarlo() {
		if (!checkNulls()) {
			trialsCounter();
			Logger.getLogger(logger).info("Starting simulation");

			// System.err.println("Rozpoczêcie symulacji")
			int complexCounter = 0;
			
			  for (int i = 0; i < nTrialsCounter; i++) { if
			  (checkIntegrity((createStepGraph()))) complexCounter++; }
			 
			// int i =1;
			/*nTrialsCounter = 1;
			while (nTrialsCounter < maxTrialsParam) {
				if (checkIntegrity((createStepGraph())))
					complexCounter++;
				if (nTrialsCounter % minTrialsParam == 0) {
					if (100 * complexCounter / nTrialsCounter >= trustParam)
						break;

				}
				nTrialsCounter++;
			}*/

			// zapis wyniku
			GraphResults results = new GraphResults();
			results.setComplexCount(complexCounter);
			results.setVertexNumber(vertexNumber);
			results.setEdgeNumber(noErrorRate);
			results.setnTrialsCount(nTrialsCounter);
			results.setTrustParam(trustParam);
			results.setErrorApp(errorAppr);
			graphResultsDAO.getGraphResults().add(results);
			graphResultsDAO.save();
		}

	}

	/**
	 * makes initial checking of nulls
	 * 
	 * @return
	 */
	private boolean checkNulls() {
		if (trustParam == null)
			return true;

		if (testGraph == null)
			return true;
		if (noErrorRate == null)
			return true;
		if (complexity == null)
			return true;
		return false;
	}

	// sekcja do sterowania widokiem
	/**
	 * resets the application
	 */
	public void reset() {
		complexity = 100;
		vertexNumber = 2;
		testGraph = null;
		neighbours = null;
		progressBarBean.setCurrentValue(-1L);

	}

	/**
	 * view option
	 * 
	 * @return
	 */
	public boolean getStep2Rendered() {
		return !(testGraph == null);

	}

	/**
	 * view option
	 * 
	 * @return
	 */
	public boolean getStep1Rendered() {

		return testGraph == null;
	}

	/**
	 * view option
	 * 
	 * @return
	 */
	public boolean getResultsButtonRendered() {
		if (checkNulls())
			return false;
		if (getStep1Rendered())
			return false;
		return true;
	}

	/**
	 * returns test graph
	 * 
	 * @return
	 */
	public NetworkGraph getTestGraph() {
		return testGraph;
	}

	/**
	 * returns amount of trials
	 * 
	 * @return
	 */
	public Integer getnTrialsCounter() {
		return nTrialsCounter;
	}

	/***
	 * sets amount of trials
	 * 
	 * @param nTrialsCounter
	 */
	public void setnTrialsCounter(Integer nTrialsCounter) {
		this.nTrialsCounter = nTrialsCounter;
	}

	/**
	 * sets trust param
	 * 
	 * @param trustParam
	 */
	public void setTrustParam(Integer trustParam) {
		this.trustParam = trustParam;
	}

	/**
	 * return trustParam
	 * 
	 * @return
	 */
	public Integer getTrustParam() {
		return trustParam;
	}

	/**
	 * returns Neighbour list
	 * 
	 * @return
	 */
	public List<String> getNeighboursList() {
		if (neighbours == null) {
			neighbours = new ArrayList<String>();
			StringBuilder tmp;
			for (int i = 0; i < testGraph.getSwitches().size(); i++) {
				tmp = new StringBuilder();
				tmp.append(testGraph.getSwitches().get(i).getName() + ": ");
				for (int i2 = 0; i2 < testGraph.getConnections().size(); i2++) {
					if (testGraph.getConnections().get(i2).getSource().equals(
							testGraph.getSwitches().get(i)))
						tmp.append(testGraph.getConnections().get(i2)
								.getDestination().getName()
								+ ", ");
					else if (testGraph.getConnections().get(i2)
							.getDestination().equals(
									testGraph.getSwitches().get(i)))
						tmp.append(testGraph.getConnections().get(i2)
								.getSource().getName()
								+ ", ");

				}
				neighbours.add(tmp.toString());
			}
		}

		return neighbours;
	}

	/*
	 * public void setProgressBarBean(ProgressBarBean progressBarBean) {
	 * this.progressBarBean = progressBarBean; }
	 * 
	 * public ProgressBarBean getProgressBarBean() { return progressBarBean; }
	 */
	public void setErrorAppr(Integer errorAppr) {
		this.errorAppr = errorAppr;
	}

	public Integer getErrorAppr() {
		return errorAppr;
	}
}
