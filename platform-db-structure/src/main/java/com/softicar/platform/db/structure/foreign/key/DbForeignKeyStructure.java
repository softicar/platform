package com.softicar.platform.db.structure.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbForeignKeyStructureSqlGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DbForeignKeyStructure implements IDbForeignKeyStructure {

	private final IDbTableStructure tableStructure;
	private Optional<String> name;
	private DbTableName foreignTableName;
	private final List<DbForeignKeyColumnPair> columnPairs;
	private final Map<String, String> columnMap;
	private DbForeignKeyAction onDeleteAction;
	private DbForeignKeyAction onUpdateAction;

	public DbForeignKeyStructure(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
		this.name = Optional.empty();
		this.foreignTableName = null;
		this.columnPairs = new ArrayList<>();
		this.columnMap = new TreeMap<>();
		this.onDeleteAction = DbForeignKeyAction.getDefault();
		this.onUpdateAction = DbForeignKeyAction.getDefault();
	}

	public DbForeignKeyStructure(IDbTableStructure tableStructure, IDbForeignKeyStructure foreignKeyStructure) {

		this.tableStructure = tableStructure;
		this.name = foreignKeyStructure.getName();
		this.foreignTableName = foreignKeyStructure.getForeignTableName();
		this.columnPairs = new ArrayList<>(foreignKeyStructure.getColumnPairs());
		this.columnMap = new TreeMap<>(foreignKeyStructure.getColumnNameMap());
		this.onDeleteAction = foreignKeyStructure.getOnDeleteAction();
		this.onUpdateAction = foreignKeyStructure.getOnUpdateAction();
	}

	public DbForeignKeyStructure(IDbForeignKeyStructure foreignKeyStructure) {

		this(foreignKeyStructure.getTableStructure(), foreignKeyStructure);
	}

	@Override
	public void validate() {

		Objects.requireNonNull(foreignTableName, "No foreign table name of foreign key specified.");
		if (columnPairs.isEmpty()) {
			throw new IllegalStateException("No foreign key columns specified.");
		}
	}

	@Override
	public IDbTableStructure getTableStructure() {

		return tableStructure;
	}

	@Override
	public Optional<String> getName() {

		return name;
	}

	@Override
	public String getForeignColumnName(String column) {

		return columnMap.get(column);
	}

	@Override
	public Map<String, String> getColumnNameMap() {

		return Collections.unmodifiableMap(columnMap);
	}

	@Override
	public List<DbForeignKeyColumnPair> getColumnPairs() {

		return Collections.unmodifiableList(columnPairs);
	}

	@Override
	public List<String> getColumns() {

		return columnPairs.stream().map(DbForeignKeyColumnPair::getSourceColumn).collect(Collectors.toList());
	}

	@Override
	public List<String> getForeignColumns() {

		return columnPairs.stream().map(DbForeignKeyColumnPair::getTargetColumn).collect(Collectors.toList());
	}

	@Override
	public DbTableName getForeignTableName() {

		return foreignTableName;
	}

	@Override
	public DbForeignKeyAction getOnDeleteAction() {

		return onDeleteAction;
	}

	@Override
	public DbForeignKeyAction getOnUpdateAction() {

		return onUpdateAction;
	}

	@Override
	public String toString() {

		return new DbForeignKeyStructureSqlGenerator(this).toString();
	}

	// ------------------------------ mutators ------------------------------ //

	public DbForeignKeyStructure setName(String name) {

		this.name = Optional.of(name);
		return this;
	}

	public DbForeignKeyStructure setName(Optional<String> name) {

		this.name = name;
		return this;
	}

	public DbForeignKeyStructure setForeignTableName(DbTableName foreignTableName) {

		this.foreignTableName = foreignTableName;
		return this;
	}

	public DbForeignKeyStructure addColumnPair(String sourceColumn, String targetColumn) {

		return addColumnPair(new DbForeignKeyColumnPair(sourceColumn, targetColumn));
	}

	public DbForeignKeyStructure addColumnPair(DbForeignKeyColumnPair columnPair) {

		this.columnPairs.add(columnPair);
		this.columnMap.put(columnPair.getSourceColumn(), columnPair.getTargetColumn());
		return this;
	}

	public DbForeignKeyStructure addColumnPairs(Collection<DbForeignKeyColumnPair> columnPairs) {

		columnPairs.forEach(this::addColumnPair);
		return this;
	}

	public DbForeignKeyStructure setOnDeleteAction(DbForeignKeyAction onDeleteAction) {

		this.onDeleteAction = onDeleteAction;
		return this;
	}

	public DbForeignKeyStructure setOnUpdateAction(DbForeignKeyAction onUpdateAction) {

		this.onUpdateAction = onUpdateAction;
		return this;
	}

	public DbForeignKeyStructure clearColumns() {

		this.columnPairs.clear();
		this.columnMap.clear();
		return this;
	}
}
