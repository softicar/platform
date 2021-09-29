package com.softicar.platform.db.runtime.key;

/**
 * Represents the primary key of a database table;
 *
 * @param <R>
 *            the type of the table rows
 * @param <P>
 *            the type of the primary key values
 * @author Oliver Richers
 */
public interface IDbTableKey<R, P> extends IDbPrimaryKey<R, P> {

	// TODO replace this interface in favor of IDbPrimaryKey
}
