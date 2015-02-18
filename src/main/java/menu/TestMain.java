package menu;

import menu.Menu;

public class TestMain {
	
	public static void main(String[] args){
		Menu menu1 = new Menu("hauptmenu1");
		Menu menu2 = new Menu("menu2");
		menu1.addSubMenu(new Menu("untermenu2"));
		menu1.addSubMenu(new Menu("untermenu3"));
		System.out.println(menu1.display());
		
	}
	


}