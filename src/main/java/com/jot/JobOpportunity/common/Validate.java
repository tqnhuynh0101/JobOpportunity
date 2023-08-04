package com.jot.JobOpportunity.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	
	private static Matcher matcher;
	
	private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	
	private static final String TEL_REGEX = "^0([3|5|7|8|9])([0-9]){8}$";
	
	private static Pattern pattern;

	public static boolean validateEmail(String regex) {
		pattern = Pattern.compile(EMAIL_REGEX);
		matcher = pattern.matcher(regex);
		return matcher.matches();
	}
	
	public static boolean validateTel(String regex) {
		pattern = Pattern.compile(TEL_REGEX);
		matcher = pattern.matcher(regex);
		return matcher.matches();
	}
}
