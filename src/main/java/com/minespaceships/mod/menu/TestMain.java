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
		Menu menu3= new Menu("Untermenu3 mit Untermenu");
		menu2.addSubMenu(menu3);
		menu2.addSubMenu(new Menu("untermenu4"));
		menu3.addSubMenu(new Menu("untermenu 5"));
		menu3.addSubMenu(new Menu("untermenu6"));

		menu1.display();

		userInput(menu1);
		menu1.switchMenu(input);
		
		/*
		System.out.print("> ");
		input = scanner.next();
		menu1.switchMenu(input);
		
		System.out.print("> ");
		input = scanner.next();
		menu3.switchMenu(input);
		*/
		
		scanner.close();
	}
	
	private static void userInput(Menu menu){
		System.out.print("> ");
		input = scanner.next();
		while(!input.equals("quit")){
			menu.switchMenu(input);
			userInput(menu);
		}
		
	}
	


}