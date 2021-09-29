package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowNullValue;
import com.softicar.platform.db.structure.enums.value.converter.DbEnumTableRowValueToIntegerConverter;
import com.softicar.platform.db.structure.enums.value.converter.DbEnumTableRowValueToStringConverter;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class DbEnumTableRowStructure implements IDbEnumTableRowStructure {

	private final IDbTableStructure tableStructure;
	private final IDbColumnStructure idColumn;
	private final IDbColumnStructure nameColumn;
	private final Map<IDbColumnStructure, IDbEnumTableRowValue> map;

	public DbEnumTableRowStructure(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
		this.idColumn = tableStructure.getIdColumn().orElseThrow(() -> new MissingColumnException("ID", tableStructure.getTableName()));
		this.nameColumn = getEnumTableNameColumn().orElseThrow(() -> new MissingColumnException("name", tableStructure.getTableName()));
		this.map = new TreeMap<>(Comparator.comparing(IDbColumnStructure::getNameOrThrow));
	}

	@Override
	public IDbTableStructure getTableStructure() {

		return tableStructure;
	}

	@Override
	public Integer getId() {

		return map.get(idColumn).convert(new DbEnumTableRowValueToIntegerConverter());
	}

	@Override
	public Optional<String> getName() {

		return Optional.of(map.get(nameColumn).convert(new DbEnumTableRowValueToStringConverter()));
	}

	@Override
	public IDbEnumTableRowValue getValue(IDbColumnStructure columnStructure) {

		return map.getOrDefault(columnStructure, DbEnumTableRowNullValue.getInstance());
	}

	@Override
	public Collection<IDbColumnStructure> getDefinedColumns() {

		return map.keySet();
	}

	@Override
	public void validate() {

		Objects.requireNonNull(getId(), "No enum ID speficied.");
		Objects.requireNonNull(getName().orElse(null), "No enum name speficied.");
	}

	@Override
	public String toString() {

		TreeMap<String, IDbEnumTableRowValue> values = new TreeMap<>();
		map.entrySet().forEach(it -> values.put(it.getKey().getNameOrThrow(), it.getValue()));
		return values.toString();
	}

	public DbEnumTableRowStructure setValue(IDbColumnStructure columnStructure, IDbEnumTableRowValue value) {

		map.put(columnStructure, value);
		return this;
	}

	/**
	 * Determines the {@link IDbColumnStructure} that corresponds to the "name"
	 * column of the given {@link IDbTableStructure} of an enum table.
	 *
	 * @return the {@link IDbColumnStructure} that corresponds to the "name"
	 *         column of the given {@link IDbTableStructure}
	 */
	private Optional<IDbColumnStructure> getEnumTableNameColumn() {

		return tableStructure//
			.getColumns()
			.stream()
			.filter(columnStructure -> columnStructure.getLogicalName().equalsIgnoreCase(DbEnumTables.NAME_COLUMN_LOGICAL_NAME))
			.findFirst();
	}

	private class MissingColumnException extends IllegalArgumentException {

		public MissingColumnException(String columnType, DbTableName tableName) {

			super(String.format("Missing '%s' column in enum table '%s'.", columnType, tableName));
		}
	}
}
