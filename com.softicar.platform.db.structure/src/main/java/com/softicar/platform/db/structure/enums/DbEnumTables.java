package com.softicar.platform.db.structure.enums;

/**
 * Aggregates constants related to enum tables.
 *
 * @author Alexander Schmidt
 */
public interface DbEnumTables {

	/**
	 * The magical comment that designates a database table as an enum table.
	 */
	final String MARKER_COMMENT = "@enum@";

	/**
	 * The expected logical name for a column to be considered the 'name' column
	 * of an enum table.
	 */
	final String NAME_COLUMN_LOGICAL_NAME = "name";
}
