package org.yacekm.Utils;

import java.util.ArrayList;
import java.util.List;

public class Util {
	private static Util instance = null;
	private static List<SearchResult> resultsDatabase;

	public Util(){
		resultsDatabase = new ArrayList<SearchResult>();
		resultsDatabase.add(new SearchResult(1, "Lady", "A lady with a coffee", "//images//default1.jpeg"));
		resultsDatabase.add(new SearchResult(2, "Car", "Car for sale", "//images//default2.jpeg"));
		resultsDatabase.add(new SearchResult(3, "Astronaut", "In a space", "//images//default3.jpeg"));
		resultsDatabase.add(new SearchResult(4, "Fish", "Life in an underworld", "//images//default4.jpeg"));
		resultsDatabase.add(new SearchResult(5, "Muscles", "Neck muscles", "//images//default5.jpeg"));
		resultsDatabase.add(new SearchResult(6, "Parenthood", "Father and a child", "//images//default6.jpeg"));
		resultsDatabase.add(new SearchResult(7, "Girl", "Frightened model", "//images//default7.jpeg"));
	}
	
	public void setResultsDatabase(List<SearchResult> resultsDatabase) {
		Util.resultsDatabase = resultsDatabase;
	}

	public static List<SearchResult> getResultsDatabase() {
		return resultsDatabase;
	}

	public static Util getInstance() {
		if(instance == null)
			instance = new Util();
		return instance;
	}
}