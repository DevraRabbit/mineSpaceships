package com.minespaceships.mod.menu;

import java.util.Scanner;
import com.minespaceships.mod.menu.Menu;

public class TestMain {
	
	private static Scanner scanner = new Scanner(System.in);
	private static String input;
	
	public static void main(String[] args){
		
		Menu menu1 = new Menu("Root 0");
		Menu menu2 = new Menu("Hauptmenu 1");
		menu1.addSubMenu(menu2);
		menu1.addSubMenu(new Menu("untermenu2"));
		menu2.addSubMenu(new Menu("untermenu3"));
		menu2.addSubMenu(new Menu("untermenu4"));

		System.out.println(menu1.display());
		
		System.out.print("> ");
		input = scanner.next();
		menu1.switchMenu(input);
		
	}
	


}