package com.softicar.platform.db.structure.table;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class DbTableStructure implements IDbTableStructure {

	private final DbTableName tableName;
	private final List<IDbColumnStructure> columnStructures;
	private final List<IDbKeyStructure> keyStructures;
	private final List<IDbForeignKeyStructure> foreignKeyStructures;
	private final List<IDbEnumTableRowStructure> enumTableRowStructures;
	private final Map<String, IDbColumnStructure> columnStructuresMap;
	private boolean enumTable;
	private String comment;

	public DbTableStructure(DbTableName tableName) {

		this.tableName = tableName;
		this.columnStructures = new ArrayList<>();
		this.keyStructures = new ArrayList<>();
		this.foreignKeyStructures = new ArrayList<>();
		this.enumTableRowStructures = new ArrayList<>();
		this.columnStructuresMap = new TreeMap<>();
		this.enumTable = false;
		this.comment = "";
	}

	@Override
	public DbTableName getTableName() {

		return tableName;
	}

	@Override
	public List<IDbColumnStructure> getColumns() {

		return Collections.unmodifiableList(columnStructures);
	}

	@Override
	public List<IDbKeyStructure> getKeys() {

		return Collections.unmodifiableList(keyStructures);
	}

	@Override
	public List<IDbForeignKeyStructure> getForeignKeys() {

		return Collections.unmodifiableList(foreignKeyStructures);
	}

	@Override
	public Optional<IDbColumnStructure> getColumnByPhysicalName(String columnName) {

		return Optional.ofNullable(columnStructuresMap.get(columnName));
	}

	@Override
	public IDbColumnStructure getColumnByPhysicalNameOrThrow(String columnName) {

		return getColumnByPhysicalName(columnName)//
			.orElseThrow(() -> new DbMissingTableColumnException(tableName, columnName));
	}

	@Override
	public Optional<IDbKeyStructure> getPrimaryKey() {

		return determinePrimaryKey();
	}

	@Override
	public Optional<IDbColumnStructure> getPkColumn() {

		return determinePkColumn();
	}

	@Override
	public Optional<IDbColumnStructure> getIdColumn() {

		return determineIdColumn();
	}

	@Override
	public List<IDbEnumTableRowStructure> getEnumTableRows() {

		return enumTableRowStructures;
	}

	@Override
	public boolean isEnumTable() {

		return enumTable;
	}

	@Override
	public String getComment() {

		return comment;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Table name: ").append(getTableName().getQuoted()).append("\n");

		builder.append("\tColumns:\n");
		for (IDbColumnStructure columnStructure: getColumns()) {
			builder.append("\t\t").append(columnStructure.toString()).append("\n");
		}

		builder.append("\tKeys:\n");
		for (IDbKeyStructure keyStructure: getKeys()) {
			builder.append("\t\t").append(keyStructure.toString()).append("\n");
		}

		builder.append("\tForeign Keys:\n");
		for (IDbForeignKeyStructure foreignKeyStructure: getForeignKeys()) {
			builder.append("\t\t").append(foreignKeyStructure.toString()).append("\n");
		}

		return builder.toString();
	}

	public void validate() {

		new DbTableStructureValidator(this).validate();
	}

	// ------------------------------ mutators ------------------------------ //

	public DbTableStructure addColumnStructure(IDbColumnStructure columnStructure) {

		this.columnStructures.add(columnStructure);
		this.columnStructuresMap.put(columnStructure.getNameOrThrow(), columnStructure);
		return this;
	}

	public DbTableStructure addKeyStructure(IDbKeyStructure keyStructure) {

		this.keyStructures.add(keyStructure);
		return this;
	}

	public DbTableStructure addForeignKeyStructure(IDbForeignKeyStructure foreignKeyStructure) {

		this.foreignKeyStructures.add(foreignKeyStructure);
		return this;
	}

	public DbTableStructure addEnumTableRow(IDbEnumTableRowStructure tableRowStructure) {

		enumTableRowStructures.add(tableRowStructure);
		return this;
	}

	public DbTableStructure setEnumTable(boolean enumTable) {

		this.enumTable = enumTable;
		return this;
	}

	public void setComment(String comment) {

		this.comment = comment;
	}

	// ------------------------------ private ------------------------------ //

	private Optional<IDbKeyStructure> determinePrimaryKey() {

		return keyStructures//
			.stream()
			.filter(key -> key.getType() == DbKeyType.PRIMARY)
			.findFirst();
	}

	private Optional<IDbColumnStructure> determinePkColumn() {

		return determinePrimaryKey()//
			.map(key -> key.getColumns())
			.filter(columns -> columns.size() == 1)
			.map(columns -> columns.get(0));
	}

	private Optional<IDbColumnStructure> determineIdColumn() {

		return determinePkColumn().filter(IDbColumnStructure::isAutoIncrement);
	}
}
