package com.softicar.platform.db.structure.column;

/**
 * Enumerates possible types of default value definitions in
 * {@link IDbColumnStructure} instances.
 *
 * @author Oliver Richers
 */
public enum DbColumnDefaultType {

	/**
	 * Indicates that no default value is defined.
	 */
	NONE,

	/**
	 * Indicates that there is an explicit, literal default value.
	 */
	NORMAL,

	/**
	 * Indicates that the default value is NULL.
	 */
	NULL,

	/**
	 * Indicates that the current time stamp is used as the default value.
	 */
	CURRENT_TIMESTAMP
}
