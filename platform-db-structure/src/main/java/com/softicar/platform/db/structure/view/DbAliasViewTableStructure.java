package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyColumnPair;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Optional;

public class DbAliasViewTableStructure extends DbTableStructure {

	private final DbAliasViewColumnMap columnMap;
	private final IDbTableStructure tableStructure;

	public DbAliasViewTableStructure(DbAliasView aliasView, IDbTableStructure tableStructure) {

		super(aliasView.getViewName());

		this.columnMap = aliasView.getColumnMap();
		this.tableStructure = tableStructure;

		copyColumnStructures();
		copyKeyStructures();
		copyForeignKeyStructures();
		copyEnumTableProperties();
		copyTableComment();
	}

	// ------------------------------ columns ------------------------------ //

	private void copyColumnStructures() {

		columnMap//
			.getColumnPairs()
			.stream()
			.filter(this::isValid)
			.map(this::copyColumnStructure)
			.forEach(this::addColumnStructure);
	}

	private boolean isValid(DbAliasViewColumnPair columnPair) {

		return columnPair.getTableColumnStructure(tableStructure).isPresent();
	}

	private DbColumnStructure copyColumnStructure(DbAliasViewColumnPair columnPair) {

		return new DbColumnStructure(this, columnPair.getTableColumnStructure(tableStructure).get()).setName(columnPair.getViewColumn());
	}

	// ------------------------------ keys ------------------------------ //

	private void copyKeyStructures() {

		tableStructure//
			.getKeys()
			.stream()
			.map(this::copyKeyStructure)
			.forEach(this::addKeyStructure);
	}

	private DbKeyStructure copyKeyStructure(IDbKeyStructure original) {

		DbKeyStructure copy = new DbKeyStructure(this, original).clearColumns();
		original//
			.getColumnNames()
			.stream()
			.map(columnMap::getViewColumnForTableColumn)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(copy::addColumnName);
		return copy;
	}

	// ------------------------------ foreign keys ------------------------------ //

	private void copyForeignKeyStructures() {

		tableStructure//
			.getForeignKeys()
			.stream()
			.map(this::copyForeignKeyStructure)
			.forEach(this::addForeignKeyStructure);
	}

	private DbForeignKeyStructure copyForeignKeyStructure(IDbForeignKeyStructure original) {

		DbForeignKeyStructure copy = new DbForeignKeyStructure(this, original).clearColumns();
		original//
			.getColumnPairs()
			.stream()
			.map(this::copyForeignKeyColumnPair)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(copy::addColumnPair);
		return copy;
	}

	private Optional<DbForeignKeyColumnPair> copyForeignKeyColumnPair(DbForeignKeyColumnPair original) {

		return columnMap//
			.getViewColumnForTableColumn(original.getSourceColumn())
			.map(viewColumnName -> new DbForeignKeyColumnPair(viewColumnName, original.getTargetColumn()));
	}

	// ------------------------------ enum table ------------------------------ //

	private void copyEnumTableProperties() {

		setEnumTable(tableStructure.isEnumTable());
		tableStructure.getEnumTableRows().forEach(this::addEnumTableRow);
	}

	// ------------------------------ comment ------------------------------ //

	private void copyTableComment() {

		setComment(tableStructure.getComment());
	}
}
