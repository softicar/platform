package com.softicar.platform.db.core;

/**
 * Provides utility methods useful when working with database statements.
 * 
 * @author Oliver Richers
 */
public class DbStatements {

	/**
	 * Creates a list of question marks useful for prepared statements.
	 * <p>
	 * FIXME: What to return if the count is zero?
	 * 
	 * @param count
	 *            the number of question marks
	 * @return something like this: (?,?,?,?,?,....)
	 */
	public static String createQuestionMarkList(int count) {

		if (count > 0) {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < count; ++i) {
				result.append(",?");
			}
			result.setCharAt(0, '(');
			result.append(')');
			return result.toString();
		} else {
			return "";
		}
	}

	/**
	 * Creates a list of question marks useful for prepared statements.
	 * <p>
	 * FIXME: What to return if the iterable is empty?
	 * 
	 * @param iterable
	 *            an iterable object, its size determines the number of question
	 *            marks
	 * @return something like this: (?,?,?,?,?,....)
	 */
	public static String createQuestionMarkList(Iterable<?> iterable) {

		StringBuilder result = new StringBuilder();

		for (@SuppressWarnings("unused")
		Object dummy: iterable) {
			result.append(",?");
		}

		// add closing brace and remove trailing comma
		int n = result.length();
		if (n > 0) {
			result.setCharAt(0, '(');
			result.append(')');
			return result.toString();
		} else {
			return "()";
		}
	}
}
