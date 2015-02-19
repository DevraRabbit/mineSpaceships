package com.minespaceships.mod.menu;

import java.util.ArrayList;

/**
 * The Menu class implements a menu structure and the ability
 * to go through these structure out of menus and sub menus.
 * @author DieDiren, ovae.
 * @verion 20150219
 */
public class Menu{

	/**
	 * The childrenList contains all sub menus.
	 */
	private final ArrayList<Menu> childrenList;

	/**
	 * The menuList contains all existing menus.
	 */
	private final static ArrayList<Menu> menuList= new ArrayList<Menu>();

	/**
	 * The name of the menu.
	 */
	private final String menuName;

	/**
	 * The currently selected Menu Object.
	 */
	private static Menu selectedMenu;

	/**
	 * The menuIdcounter ensures that each new menu gets a unique id.
	 * It contains the last assigned ID.
	 */
	private static int menuIDcounter;

	/**
	 * The menu id of this menu.
	 */
	private final int menuID;

	/**
	 * The parent/mother Menu of this menu.
	 */
	private Menu motherMenu;

	/**
	 * The menu constructor creates a new menu object with a menu name.
	 * @param name
	 */
	public Menu(final String name){
		if(name.trim().isEmpty()){
			throw new IllegalArgumentException("The menu name can not be null");
		}
		
		menuName = name;
		motherMenu=this;
		childrenList=new ArrayList<Menu>();
		this.menuID = menuIDcounter;
		menuIDcounter++;
		
	}

	/**
	 * Prints out a List of all sub menus in the terminal.
	 */
	public void display(){
		String out = "";
		//add the menu name
		out += "["+getMenuID()+"] "+getMenuName()+ '\n';
		//add all sub menus to the string.
		for(Menu child: childrenList){
			out+= "\t ["+child.getMenuID()+"] "+child.getMenuName()+ '\n';
		}
		System.out.println(out);
	}

	/**
	 * returns true if successful, false if the ID was not correct
	 * changes the static variable selectedMenu to the new Menu
	 */
	public boolean switchMenu(String name){
		int n;
		try
		{	
			if(name.equals("m")){
				if(selectedMenu != null){
					selectedMenu.getMother().display();
					selectedMenu = this;
				}
				System.out.println("");
				return true;
			}
			
			if(name.length()>1){
			String name2= name.substring(0,2);
			
			if(name2.equals("id")){
				n = Integer.parseInt(name.substring(2));
				if(n < 0 || n > menuIDcounter)
				{
					throw new Exception("ungültige ID");
				}
				
				for(Menu m : menuList)
				{
					if(m.getMenuID() == n)
					{
						selectedMenu = m;
						m.display();
					}
				}
				return true;
			}
			}
			
			int o;
			o = Integer.parseInt(name);
					
			
					selectedMenu = selectedMenu.childrenList.get(o);
					selectedMenu.display();
				
			
			
			
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
		
	}

	/**
	 * Adds a menu to the childrenList of this menu.
	 * @param menu
	 */
	public void addSubMenu(Menu menu){
		menu.setMotherMenu(this);
		childrenList.add(menu);
		menuList.add(menu);
	}

	/**
	 * Set the mother menu.
	 * @param menu
	 */
	private void setMotherMenu(Menu menu){
		motherMenu=menu;
	}

	/**
	 * Returns the mother menu.
	 * @return motherMenu
	 */
	private Menu getMother(){
		return motherMenu;
	}

	/**
	 * Returns the MenuID.
	 * @return menuID
	 */
	public int getMenuID(){
		return this.menuID;
	}

	/**
	 * The toString method returns the menus name.
	 */
	@Override
	public String toString(){
		return this.menuName;
	}

	/**
	 * Returns the childrenList.
	 * @return childrenList
	 */
	private ArrayList getChildrenList(){
		return this.childrenList;
	}

	/**
	 * Returns the selected Menu.
	 * @return selectedMenu
	 */
	public static Menu getSelectedMenu(){
		return selectedMenu;
	}

	/**
	 * Returns the menus name.
	 * @return menuName
	 */
	public String getMenuName(){
		return menuName;
	}

}