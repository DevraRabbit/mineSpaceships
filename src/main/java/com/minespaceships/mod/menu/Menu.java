package com.minespaceships.mod.menu;

import java.util.ArrayList;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * The Menu class implements a menu structure and the ability
 * to go through these structure out of menus and sub menus.
 * @author DieDiren, Sinan, ovae.
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
	 * 
	 */
	protected CustomGuiChat terminal;

	/**
	 * The menu constructor creates a new menu object with a menu name.
	 * @param name
	 */
	public Menu(final String name, final CustomGuiChat terminal){
		if(name.trim().isEmpty()){
			throw new IllegalArgumentException("The menu name can not be null.");
		}
		if(terminal == null){
			throw new IllegalArgumentException("the therminal ca not be null.");
		}
		
		menuName = name;
		motherMenu=this;
		this.terminal=terminal;
		childrenList=new ArrayList<Menu>();
		this.menuID = menuIDcounter;
		menuIDcounter++;
		
	}

	/**
	 * Prints out a List of all sub menus in the terminal.
	 */
	public String display(){
		selectedMenu = this;
		String out = "";
		//add the menu name
		out += "["+getMenuID()+"] "+getMenuName()+ '\n';
		//add all sub menus to the string.
		for(Menu child: childrenList){
			out+= "\t ["+child.getMenuID()+"] "+child.getMenuName()+ '\n';
		}
		return out;
	}
	
	/**
	 * returns true if successful, false if the ID was not correct
	 * changes the static variable selectedMenu to the new Menu
	 */
	public boolean switchMenu(final String name){
		int n;
		String paramlist = null;
		try{
			//parent menu
			if(name.equals("m") || name.equals("up") || name.equals("parent")){
				if(selectedMenu != null){
					terminal.display(selectedMenu.getMother().display());
					selectedMenu = this;
				}
				System.out.println("");
				return true;
			}

			//control by id
			if(name.length()>1){
				String nameSub= name.substring(0,2);
				if(nameSub.equals("id") || nameSub.equals("ID")){
					n = Integer.parseInt(name.substring(2));
					if(n < 0 || n > menuIDcounter){
						throw new Exception("ungültige ID");
					}

					//
					for(Menu menu : menuList){
						if(menu.getMenuID() == n){
							selectedMenu = menu;
							functionMenu(menu, paramlist);
						}
					}
					return true;
				}
			}

			//control by menu name
			if(name.length() >= 4){
				for(Menu menu : menuList){
					if(menu.getMenuName().equals(name)){
						selectedMenu = menu;
						functionMenu(menu, paramlist);
					}
				}
				return true;
			}

			//control by sub menu position
			int position = Integer.parseInt(name);
			if(position > 0 && position <= selectedMenu.childrenList.size()){
				position -= 1;
				if(selectedMenu instanceof Menu){
					selectedMenu = selectedMenu.childrenList.get(position);
					terminal.display(selectedMenu.display());
				}else{
					functionMenu(selectedMenu.childrenList.get(position), paramlist);
				}
				return true;
			}

			return false;
		}catch(IndexOutOfBoundsException e){
			System.err.println("IndexOutOfBoundsException");
			return false;
		}catch(IllegalArgumentException f){
			System.err.println("IllegalArgumentException");
			return false;
		}catch(Exception g){
			System.err.println("Exception");
			return false;
		}
	}

	/**
	 * Gets a command
	 * @param name
	 * @return ArrayList<Strings>
	 */
	private ArrayList<String> extractParams(final String command){
		ArrayList<String> list = new ArrayList<String>();
		
		return list;
	}

	/**
	 * 
	 */
	private void functionMenu(final Menu menu, final String paramlist){
		if(menu instanceof FunktionalMenu){
			((FunktionalMenu)menu).activate(paramlist);
		}else{
			terminal.display(menu.display());
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