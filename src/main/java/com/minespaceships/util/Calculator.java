package com.minespaceships.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class Calculator {
	public static void calc(String command, EntityPlayer player) {
		//prepare the math expression
		command = command.substring("calc".length()).replaceAll("\\s", ""); 
		try {
			player.addChatComponentMessage(new ChatComponentText(command + " = " + solve(command)));
		} catch (Exception ex) {
			player.addChatComponentMessage(new ChatComponentText("calc: Error processing intput"));
		}
	}
	
	//Attributes for math parser
	private static Pattern brackets = Pattern.compile("\\(.*?\\)");
	private static Pattern times = Pattern.compile("(\\-?[0-9\\.]+)\\*(\\-?[0-9\\.]+)");
	private static Pattern div = Pattern.compile("(\\-?[0-9\\.]+)/(\\-?[0-9\\.]+)");
	private static Pattern plus = Pattern.compile("(\\-?[0-9\\.]+)\\+(\\-?[0-9\\.]+)");
	private static Pattern minus = Pattern.compile("(\\-?[0-9\\.]+)\\-(\\-?[0-9\\.]+)");
	private static Pattern done = Pattern.compile("\\-?[0-9\\.]+");
	private static Matcher mb;
	private static Matcher mt;
	private static Matcher md;
	private static Matcher mp;
	private static Matcher mm;
	private static Matcher mdone;
	
	//Recursively parses a String containing a math expression
	//Supported are brackets, +-*/, negative numbers and doubles 
	private static String solve(String expr) throws Exception {
		mb = brackets.matcher(expr);
		mt = times.matcher(expr);
		md = div.matcher(expr);
		mp = plus.matcher(expr);
		mm = minus.matcher(expr);
		mdone = done.matcher(expr);
		
		if(mdone.matches()) return expr;
		
		if(mb.find()) {
			Matcher m = mb;
			String before = expr.substring(0, m.start());
			String inner = expr.substring(m.start() + 1, m.end() - 1);
			String after = expr.substring(m.end());			
			return solve(before + solve(inner) + after);
		} else if (mt.find()) {
			Matcher m = mt;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) * Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (md.find()) {
			Matcher m = md;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) / Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (mp.find()) {
			Matcher m = mp;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));

			String inner = String.valueOf(Double.valueOf(first) + Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (mm.find()) {
			Matcher m = mm;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) - Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		}
		return "Error";
	}
}
