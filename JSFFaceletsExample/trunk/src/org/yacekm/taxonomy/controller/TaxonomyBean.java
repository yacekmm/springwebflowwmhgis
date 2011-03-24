package org.yacekm.taxonomy.controller;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.html.HtmlDropDownMenu;
import org.yacekm.taxonomy.model.Taxonomy;

public class TaxonomyBean {
	private Taxonomy taxonomy;
	private HtmlDropDownMenu taxMenu;
	private List<Taxonomy> taxonomyList;
	private List<String> testList;
	private String nameFilterValue = "";
	private int resultsCount = (int)Math.random();
	
	public TaxonomyBean(){
		taxonomyList = new ArrayList<Taxonomy>();
		taxonomyList.add(new Taxonomy(1, "Location"));
		taxonomyList.add(new Taxonomy(2, "People"));
		taxonomyList.add(new Taxonomy(3, "Furniture"));
		taxonomyList.add(new Taxonomy(4, "Place"));
		taxonomyList.add(new Taxonomy(5, "Animal"));
		taxonomyList.add(new Taxonomy(6, "Occupation"));
		taxonomyList.add(new Taxonomy(7, "Country"));
		taxonomyList.add(new Taxonomy(8, "Region"));
		taxonomyList.add(new Taxonomy(9, "Pizza"));
		taxonomyList.add(new Taxonomy(10, "Food"));
		taxonomyList.add(new Taxonomy(11, "Building"));
		
		taxonomy = new Taxonomy(0, "testing Tax");
		
		testList = new ArrayList<String>();
		testList.add("test 1");
		testList.add("test 2");
		testList.add("test 3");
	}
	
	public void resetFilter(){
		nameFilterValue= "";
	}
	
	public void updateCount(){
		if(Math.random()>0.4)
			resultsCount = (int)(Math.random()*20);
	}
	
	public void setTaxonomyList(List<Taxonomy> taxonomyList) {
		this.taxonomyList = taxonomyList;
	}

	public List<Taxonomy> getTaxonomyList() {
		return taxonomyList;
	}

	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}
	
	public Taxonomy getTaxonomy() {
		taxMenu = taxonomy.getDropMenu();
		return taxonomy;
	}

	public void setNameFilterValue(String nameFilterValue) {
		this.nameFilterValue = nameFilterValue;
	}

	public String getNameFilterValue() {
		return nameFilterValue;
	}

	public void setTestList(List<String> testList) {
		this.testList = testList;
	}

	public List<String> getTestList() {
		return testList;
	}

	public void setTaxMenu(HtmlDropDownMenu taxMenu) {
		this.taxMenu = taxMenu;
	}

	public HtmlDropDownMenu getTaxMenu() {
		return taxMenu;
	}

	public void setResultsCount(int resultsCount) {
		this.resultsCount = resultsCount;
	}

	public int getResultsCount() {
		updateCount();
		return resultsCount;
	}
}
