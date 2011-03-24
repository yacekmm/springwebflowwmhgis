package org.yacekm.results;

import java.util.ArrayList;
import java.util.List;

import org.yacekm.Utils.SearchResult;
import org.yacekm.Utils.Util;

public class ResultsBean {
	private List<SearchResult> results;
	
	public ResultsBean(){
		results = new ArrayList<SearchResult>(); 
		Util.getInstance();
		results = Util.getResultsDatabase();
	}

	private List<SearchResult> randomizeResults(double factor){
		List<SearchResult> res = new ArrayList<SearchResult>();
		for(int i=0;i<results.size();i++){
			if(Math.random()>factor){
				res.add(results.get(i));
			}
		}
		return res;
	}
	
	public List<SearchResult> getBestResults(){
		return randomizeResults(0.3);
	}
	
	public List<SearchResult> getBetterResults(){
		return randomizeResults(0.5);
	}
	
	public List<SearchResult> getGoodResults(){
		return randomizeResults(0.7);
	}
	
	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	public List<SearchResult> getResults() {
		return results;
	}
}
