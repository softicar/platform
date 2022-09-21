package com.softicar.platform.db.runtime.table.finder;

import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Searches the class path for database table instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbTableFinder {

	private static final String TABLE_FIELD_NAME = "TABLE";

	/**
	 * Finds all table instances in the class path.
	 *
	 * @return all table instances (never <i>null</i>)
	 */
	public Collection<IDbTable<?, ?>> findAllTables() {

		return ClasspathFilesMetadata//
			.getInstance()
			.getTransitivelyImplementingClasses(IDbTableRow.class)
			.stream()
			.map(this::getTableField)
			.flatMap(Optional::stream)
			.map(this::getTableInstance)
			.collect(Collectors.toList());
	}

	private Optional<Field> getTableField(Class<?> tableRowClass) {

		return ReflectionUtils.getDeclaredField(tableRowClass, TABLE_FIELD_NAME);
	}

	private IDbTable<?, ?> getTableInstance(Field tableField) {

		var tableInstance = ReflectionUtils.getStaticValue(tableField);
		return IDbTable.class.cast(tableInstance);
	}
}
