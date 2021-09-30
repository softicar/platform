package com.softicar.platform.db.structure.key;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbKeyStructureSqlGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbKeyStructure implements IDbKeyStructure {

	private final IDbTableStructure tableStructure;
	private DbKeyType type;
	private Optional<String> name;
	private final List<String> columnNames;

	public DbKeyStructure(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
		this.name = Optional.empty();
		this.type = null;
		this.columnNames = new ArrayList<>();
	}

	public DbKeyStructure(IDbTableStructure tableStructure, IDbKeyStructure keyStructure) {

		this.tableStructure = tableStructure;
		this.name = keyStructure.getName();
		this.type = keyStructure.getType();
		this.columnNames = new ArrayList<>(keyStructure.getColumnNames());
	}

	public DbKeyStructure(IDbKeyStructure keyStructure) {

		this(keyStructure.getTableStructure(), keyStructure);
	}

	@Override
	public void validate() {

		Objects.requireNonNull(type, "Type of key not specified.");

		if (type != DbKeyType.PRIMARY) {
			Objects.requireNonNull(name, "Name of key not specified.");
		}

		if (columnNames.isEmpty()) {
			throw new IllegalStateException("No key columns specified.");
		}
	}

	@Override
	public IDbTableStructure getTableStructure() {

		return tableStructure;
	}

	@Override
	public DbKeyType getType() {

		return type;
	}

	@Override
	public Optional<String> getName() {

		return name;
	}

	@Override
	public boolean containsColumn(IDbColumnStructure column) {

		return columnNames.contains(column.getNameOrThrow());
	}

	@Override
	public List<String> getColumnNames() {

		return Collections.unmodifiableList(columnNames);
	}

	@Override
	public List<IDbColumnStructure> getColumns() {

		return columnNames//
			.stream()
			.map(tableStructure::getColumnByPhysicalNameOrThrow)
			.collect(Collectors.toList());
	}

	@Override
	public String toString() {

		return new DbKeyStructureSqlGenerator(this).toString();
	}

	// ------------------------------  mutators ------------------------------ //

	public DbKeyStructure setType(DbKeyType type) {

		this.type = type;
		return this;
	}

	public DbKeyStructure setName(String name) {

		this.name = Optional.ofNullable(name);
		return this;
	}

	public DbKeyStructure addColumnName(String columnName) {

		this.columnNames.add(columnName);
		return this;
	}

	public DbKeyStructure addColumnNames(Collection<String> columnNames) {

		this.columnNames.addAll(columnNames);
		return this;
	}

	public DbKeyStructure clearColumns() {

		this.columnNames.clear();
		return this;
	}
}
