package org.yacekm.taxonomy.model;

import org.richfaces.component.html.HtmlDropDownMenu;
import org.richfaces.component.html.HtmlMenuGroup;
import org.richfaces.component.html.HtmlMenuItem;

public class Taxonomy {
	private int id;
	private String name;
	private HtmlMenuGroup menu;
	private HtmlDropDownMenu dropMenu; 
	
	public Taxonomy(){
		initMenu();
		initDropMenu();
	}
	
	public Taxonomy(int _id, String _name){
		id = _id;
		name = _name;
		initMenu();
		initDropMenu();
	}
	
	private void initMenu() {
		menu = new HtmlMenuGroup();
		menu.setValue(name + "_menu");
		HtmlMenuItem item;

		for(int i=1;i<5;i++){
			item = new HtmlMenuItem();
			item.setId("_" + Integer.toString(i));
			item.setSubmitMode("server");
			item.setValue(name + "-Suboption-" + Integer.toString(i));
			menu.getChildren().add(item);
		}
	}
	
	private void initDropMenu() {
		dropMenu = new HtmlDropDownMenu();
		dropMenu.setValue(name + "_menu");
		HtmlMenuItem item;

		for(int i=1;i<5;i++){
			item = new HtmlMenuItem();
			item.setId("_" + Integer.toString(i));
			item.setSubmitMode("server");
			item.setValue(name + "-Suboption-" + Integer.toString(i));
			dropMenu.getChildren().add(item);
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setMenu(HtmlMenuGroup menu) {
		this.menu = menu;
	}

	public HtmlMenuGroup getMenu() {
		return menu;
	}

	public void setDropMenu(HtmlDropDownMenu dropMenu) {
		this.dropMenu = dropMenu;
	}

	public HtmlDropDownMenu getDropMenu() {
		System.out.println("!!!!!!!!!!!!!!!!!returning drop menu! name :" + name);
		return dropMenu;
	}
}
