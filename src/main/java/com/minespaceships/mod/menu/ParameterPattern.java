package com.minespaceships.mod.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a utility to detect parameter patterns in
 * a string.
 * @author ovae.
 * @version 20150331.
 */
public class ParameterPattern {

	//A word with at least one character.
	private static String word = "(\\w\\w*)";

	/* At least one whitespace character like
	 * spaces tabs or other white space characters.
	 */
	private static String whitespaces = "(\\s\\s*)";

	//Pattern for a float value.
	private static String floatPattern = "([+-]?\\d*[.]?\\d+);";

	//Patter for a integer value
	private static String intPattern ="([0-9]*);";

	//Any possible character
	private static String anyChar =".";

	//This Class should not be initialised.
	ParameterPattern(){}

	/**
	 * Returns true if the command parameter has the following form.
	 * Whitespace+float+whitespace+float+whitespace+float+ any number of whitespace
	 * @param command
	 * @return
	 */
	public static double[] threeParameters(String command){
		if(command.equals(null)){
			throw new IllegalArgumentException("Command can not be null.");
		}
		if(command.trim().isEmpty()){
			throw new IllegalArgumentException("Command can not be empty.");
		}

		double[] list = new double[3];
		//get the parameter without the command name.
		command = command.replaceAll(whitespaces, "");
		Pattern pattern = Pattern.compile(floatPattern+floatPattern+floatPattern);
		Matcher matcher = pattern.matcher(command);
		if(matcher.matches()){
			//get the first, second and third parameter.
			list[0] = Double.valueOf(matcher.group(1));
			list[1] = Double.valueOf(matcher.group(2));
			list[2] = Double.valueOf(matcher.group(3));
			return list;
		}else{
			return list;
		}
	}

	/**
	 * Removes all parameter and returns only the command.
	 * @return command.
	 */
	public static String getOnlyTheCommand(final String command){
		String commandPat = "([a-z\\s]+).*";
		Pattern pat = Pattern.compile(commandPat);
		Matcher match  = pat.matcher(command);
		if(match.matches()){
			return match.group(1);
		}else{
			return "";
		}
	}

}
