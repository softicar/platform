package com.softicar.platform.db.structure.key;

/**
 * Enumerates possible types of keys in {@link IDbKeyStructure} instances.
 *
 * @author Oliver Richers
 */
public enum DbKeyType {

	/**
	 * Indicates a primary key.
	 */
	PRIMARY,

	/**
	 * Indicates a unique key.
	 */
	UNIQUE,

	/**
	 * Indicates an index key.
	 */
	INDEX
}
