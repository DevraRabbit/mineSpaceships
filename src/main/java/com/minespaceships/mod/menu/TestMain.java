package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.Menu;

public class TestMain {
	
	public static void main(String[] args){
		Menu menu1 = new Menu("hauptmenu1");
		Menu menu2;
		Menu menu3;
	
		menu1.addSubMenu(menu2=new Menu("untermenu2"));
		menu1.addSubMenu(menu3=new Menu("untermenu3"));
		System.out.println(menu1.display());
		
	}
	


}