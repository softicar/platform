package com.softicar.platform.db.runtime.table.field;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Extracts the {@link IDbTable} field from a {@link IDbTableRow} class.
 *
 * @author Oliver Richers
 */
public class DbTableFieldExtractor {

	private static final String TABLE_FIELD_NAME = "TABLE";

	/**
	 * Returns the respective {@link IDbTable} instance or null.
	 *
	 * @param tableRowClass
	 *            the {@link IDbTableRow} class to extract from
	 * @return the {@link IDbTable} instance or null if extraction failed
	 */
	public IDbTable<?, ?> extractTable(Class<?> tableRowClass) {

		return getTableFieldAsOptional(tableRowClass)//
			.map(this::extractValue)
			.map(this::castToTable)
			.orElse(null);
	}

	private Optional<Field> getTableFieldAsOptional(Class<?> tableRowClass) {

		try {
			return Optional.of(tableRowClass.getDeclaredField(TABLE_FIELD_NAME));
		} catch (NoSuchFieldException exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	private Object extractValue(Field field) {

		try {
			field.setAccessible(true);
			return field.get(null);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		} catch (NullPointerException exception) {
			DevNull.swallow(exception);
			return null;
		}
	}

	private IDbTable<?, ?> castToTable(Object table) {

		if (IDbTable.class.isInstance(table)) {
			return (IDbTable<?, ?>) table;
		} else {
			return null;
		}
	}
}
