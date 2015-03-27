package com.minespaceships.mod.menu;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * The Menu class implements a menu structure and the ability
 * to go through these structure out of menus and sub menus.
 * @author DieDiren, Sinan, ovae.
 * @verion 20150225
 */
public class Menu{

	//The childrenList contains all sub menus.
	private final ArrayList<Menu> childrenList;

	//The menuList contains all existing menus.
	private final static ArrayList<Menu> menuList= new ArrayList<Menu>();

	//The name of the menu.
	private final String menuName;

	//The currently selected Menu Object.
	private static Menu selectedMenu;

	/**
	 * The menuIdcounter ensures that each new menu gets a unique id.
	 * It contains the last assigned ID.
	 */
	private static int menuIDcounter;

	//The menu id of this menu.
	private final int menuID;

	//The parent/mother Menu of this menu.
	private Menu motherMenu;

	/**
	 * The menu constructor creates a new menu object with a menu name.
	 * @param name
	 */
	public Menu(final String name){
		if(name.equals(null)){
			throw new IllegalArgumentException("The menu name can not he null");
		}
		if(name.trim().isEmpty()){
			throw new IllegalArgumentException("The menu name can not be empty.");
		}

		menuName = name;
		motherMenu=this;
		childrenList=new ArrayList<Menu>();
		this.menuID = menuIDcounter;
		menuIDcounter++;
	}

	/**
	 * Returns the menu in where you are currently in.
	 * Returns {@code null} if the input was not and id, menu position or the menu name.
	 */
	public Menu switchMenu(String name, final CustomGuiChat terminal){
		if(name.equals(null)){
			throw new IllegalArgumentException("The menu name can not he null");
		}
		if(name.trim().isEmpty()){
			throw new IllegalArgumentException("The menu name can not be empty.");
		}

		int n;
		try{
			//parent menu
			if(name.equals("m") || name.equals("up") || name.equals("parent")){
				if(selectedMenu != null){
					return selectedMenu.getMother();
				}
			}

			//For a single character in put.
			if(name.startsWith("#")){
				name = name.replaceAll("#", "");
				if(selectedMenu.getChildrenList().get(0) instanceof FunctionalParamMenu){
					Menu temp = (Menu) selectedMenu.getChildrenList().get(0);
					FunctionalParamMenu menu = (FunctionalParamMenu) selectedMenu.getChildrenList().get(0);
					menu.activate(name, terminal);
					selectedMenu = ((Menu) menu).getMother().getMother();
					return ((Menu) menu).getMother().getMother();
				}
			}

			/*
			 * Changes the menu via menu id,
			 * the input must be longer than one character.
			 */
			if(name.length()>1){
				String nameSub= name.substring(0,2);
				if(nameSub.equals("id") || nameSub.equals("ID")){
					n = Integer.parseInt(name.substring(2));
					if(n < 0 || n > menuIDcounter){
						throw new IllegalArgumentException("ungültige ID");
					}

					for(Menu menu : menuList){
						if(menu.getMenuID() == n){
							selectedMenu = menu;
							return menu;
						}
					}
				}
			}

			/*
			 * Changes the menu via menu name,
			 * the input must be longer than 4 characters.
			 */
			if(name.length() >= 4){
				for(Menu menu : menuList){
					if(menu.getMenuName().equals(name)){
						selectedMenu = menu;
						return menu;
					}
				}
			}

			//Changes the menu via sub menu position.
			int position = Integer.parseInt(name);
			if(position > 0 && position <= selectedMenu.childrenList.size()){
				position -= 1;
				return selectedMenu.childrenList.get(position);
			}
		}catch(IndexOutOfBoundsException e){
			System.err.println("IndexOutOfBoundsException appeared"+e.getStackTrace());
			//System.out.println(e.getStackTrace());
		}catch(IllegalArgumentException f){
			System.err.println("IllegalArgumentException appeared"+f.getStackTrace());
		}catch(NullPointerException g){
			System.err.println("NullPointerException appeared"+g.getStackTrace());
		}catch(Exception h){
			System.err.println("Exception appeared"+h.getStackTrace());
		}
		return null;
	}

	/**
	 * Adds a menu to the childrenList of this menu.
	 * @param menu
	 */
	public void addSubMenu(Menu menu){
		if(menu.equals(null)){
			throw new IllegalArgumentException("Menu can not be null.");
		}
		menu.setMotherMenu(this);
		childrenList.add(menu);
		menuList.add(menu);
	}

	/**
	 * Set the mother menu.
	 * @param menu
	 */
	private void setMotherMenu(Menu menu){
		if(menu.equals(null)){
			throw new IllegalArgumentException("Mother menu can not be null.");
		}
		motherMenu=menu;
	}

	/**
	 * Returns the mother menu.
	 * @return motherMenu
	 */
	public Menu getMother(){
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
	 * Returns the childrenList, witch contains all sub menus.
	 * @return childrenList
	 */
	public ArrayList getChildrenList(){
		return this.childrenList;
	}

	/**
	 * Set the selected menu.
	 * @param menu
	 */
	public static void setSelectedMenu(final Menu menu){
		if(menu.equals(null)){
			throw new IllegalArgumentException("The selected menu can not be null.");
		}
		selectedMenu = menu;
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