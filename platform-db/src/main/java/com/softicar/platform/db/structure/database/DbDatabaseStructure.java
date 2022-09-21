package com.softicar.platform.db.structure.database;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.view.IDbViewStructure;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

/**
 * Default implementation of {@link IDbDatabaseStructure}.
 *
 * @author Oliver Richers
 */
public class DbDatabaseStructure implements IDbDatabaseStructure {

	private final TreeMap<DbTableName, IDbTableStructure> tableStructures;
	private final TreeMap<DbTableName, IDbViewStructure> viewStructures;

	public DbDatabaseStructure() {

		this.tableStructures = new TreeMap<>();
		this.viewStructures = new TreeMap<>();
	}

	@Override
	public Set<DbTableName> getTableNames() {

		return tableStructures.keySet();
	}

	@Override
	public Collection<IDbTableStructure> getTableStructures() {

		return tableStructures.values();
	}

	@Override
	public IDbTableStructure getTableStructure(DbTableName tableName) {

		return tableStructures.get(tableName);
	}

	@Override
	public Set<DbTableName> getViewNames() {

		return viewStructures.keySet();
	}

	@Override
	public Collection<IDbViewStructure> getViewStructures() {

		return viewStructures.values();
	}

	@Override
	public IDbViewStructure getViewStructure(DbTableName viewName) {

		return viewStructures.get(viewName);
	}

	// ------------------------------ mutators ------------------------------ //

	public DbDatabaseStructure addTableStructure(IDbTableStructure tableStructure) {

		tableStructures.put(tableStructure.getTableName(), tableStructure);
		return this;
	}

	public DbDatabaseStructure addViewStructure(IDbViewStructure viewStructure) {

		viewStructures.put(viewStructure.getViewName(), viewStructure);
		return this;
	}
}
