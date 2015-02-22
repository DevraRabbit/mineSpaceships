package com.minespaceships.mod.menu;

import java.util.regex.Pattern;

/**
 * 
 * @author ovae.
 * @version 20150222
 */
public class ParameterPattern {

	//A word with at least one character.
	private static String word = "\\w\\w*";

	/* At least one whitespace character like
	 * spaces tabs or other white space characters.
	 */
	private static String whitespaces = "\\s\\s*";

	//Pattern for a float value.
	private static String floatPattern = "([+-]?\\d*.\\d*)";

	//Any possible character
	private static String anyChar =".";

	//This Class should not be initialised.
	private ParameterPattern(){}

	/**
	 * 
	 * @param command
	 * @return
	 */
	private static boolean hasParams(final String command){
		return Pattern.matches(word+whitespaces+anyChar, command);
	}

	/**
	 * Returns true if the command parameter has the following form.
	 * command+whitespaces+float+whitespaces+float+whitespaces+float+ any number of whitespace
	 * @param command
	 * @return
	 */
	private static boolean commandAndThreeParameters(final String command){
		return Pattern.matches(word+whitespaces+floatPattern+whitespaces+floatPattern+whitespaces+floatPattern+"([\\s]?)*", command);
	}

}
