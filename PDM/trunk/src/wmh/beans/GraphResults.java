package wmh.beans;

import wmh.dao.Const;


/**
 * Class holds results of the Monte Carlo Simulation
 * @author pko
 *
 */
public class GraphResults {
	
	private int id;
	private int vertexNumber;
	private int edgeNumber;
	private int nTrialsCount;
	private int complexCount;
	private int trustParam;
	private int errorApp;
	public int getVertexNumber() {
		return vertexNumber;
	}
	public void setVertexNumber(int vertexNumber) {
		this.vertexNumber = vertexNumber;
	}

	public void setId(int id) {
		this.id = id;	
	}
	
	public String getResult()
	{
		 double tmp = (((double)complexCount) / nTrialsCount)*100;
		return Const.format(tmp, 3, 2);
		//return null;
	}
	public int getId() {
		return id;
	}
	public int getEdgeNumber() {
		return edgeNumber;
	}
	public void setEdgeNumber(int edgeNumber) {
		this.edgeNumber = edgeNumber;
	}
	public int getnTrialsCount() {
		return nTrialsCount;
	}
	public void setnTrialsCount(int nTrialsCount) {
		this.nTrialsCount = nTrialsCount;
	}
	public int getComplexCount() {
		return complexCount;
	}
	public void setComplexCount(int complexCount) {
		this.complexCount = complexCount;
	}
	public void setTrustParam(int trustParam) {
		this.trustParam = trustParam;
	}
	public int getTrustParam() {
		return trustParam;
	}
	public void setErrorApp(int errorApp) {
		this.errorApp = errorApp;
	}
	public int getErrorApp() {
		return errorApp;
	}


	

}
