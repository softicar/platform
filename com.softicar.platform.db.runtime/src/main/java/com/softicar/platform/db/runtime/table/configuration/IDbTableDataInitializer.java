package com.softicar.platform.db.runtime.table.configuration;

/**
 * The data initializer inserts all default data into the database table.
 * <p>
 * Most database table do not have default data. This interface aims at database
 * tables with static reference data, e.g. static enumeration tables.
 *
 * @author Oliver Richers
 */
public interface IDbTableDataInitializer {

	void initializeData();
}
