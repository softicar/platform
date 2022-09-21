package com.softicar.platform.db.core.dbms.mysql;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks identifiers for validity in a MySQL context.
 *
 * @author Alexander Schmidt
 */
public class DbMysqlIdentifierValidator {

	private static final Pattern DATABASE_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9$_ ]+");
	private static final Pattern SIMPLE_TABLE_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9$_ ]+");
	private static final Pattern VARIABLE_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9$_\\. ]+");
	private static final int VARIABLE_NAME_MAX_LENGTH = 64;

	/**
	 * Validates the given database name, and throws an exception if the name is
	 * invalid.
	 *
	 * @param databaseName
	 *            the database name to validate (never null)
	 * @throws NullPointerException
	 *             if the given database name is null
	 * @throws IllegalArgumentException
	 *             if the given database name is invalid
	 */
	public static void assertValidDatabaseName(String databaseName) {

		Objects.requireNonNull(databaseName);
		if (!matchesPattern(databaseName, DATABASE_NAME_PATTERN)) {
			throw new IllegalArgumentException(String.format("Database name \"%s\" contains illegal characters.", databaseName));
		}
	}

	/**
	 * Validates the given simple table name, and throws an exception if the
	 * name is invalid.
	 *
	 * @param simpleTableName
	 *            the database name to validate (never null)
	 * @throws NullPointerException
	 *             if the given simple table name is null
	 * @throws IllegalArgumentException
	 *             if the given simple table name is invalid
	 */
	public static void assertValidSimpleTableName(String simpleTableName) {

		Objects.requireNonNull(simpleTableName);
		if (!matchesPattern(simpleTableName, SIMPLE_TABLE_NAME_PATTERN)) {
			throw new IllegalArgumentException(String.format("Simple table name \"%s\" contains illegal characters.", simpleTableName));
		}
	}

	/**
	 * Validates the given variable name, and throws an exception if the name is
	 * invalid.
	 *
	 * @param varaibleName
	 *            the variable name to validate (never null)
	 * @throws NullPointerException
	 *             if the given variable name is null
	 * @throws IllegalArgumentException
	 *             if the given variable name is invalid
	 */
	public static void assertValidVariableName(String varaibleName) {

		Objects.requireNonNull(varaibleName);
		if (varaibleName.length() > VARIABLE_NAME_MAX_LENGTH) {
			throw new IllegalArgumentException(
				String.format("Variable name \"%s\" exceeds maximum length of %s characters.", varaibleName, VARIABLE_NAME_MAX_LENGTH));
		}
		if (!matchesPattern(varaibleName, VARIABLE_NAME_PATTERN)) {
			throw new IllegalArgumentException(String.format("Variable name \"%s\" contains illegal characters.", varaibleName));
		}
	}

	private static boolean matchesPattern(String string, Pattern pattern) {

		Matcher matcher = pattern.matcher(string);
		return matcher.find() && matcher.hitEnd();
	}
}
