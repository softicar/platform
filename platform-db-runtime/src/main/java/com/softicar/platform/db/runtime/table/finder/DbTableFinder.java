package com.softicar.platform.db.runtime.table.finder;

import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Searches the class path for database table instances.
 *
 * @param <T>
 *            the super type of the table instances to find (e.g.
 *            {@link IDbTable})
 * @param <R>
 *            the super type of the row type of the table instances to find
 *            (e.g. {@link IDbTableRow})
 * @author Alexander Schmidt
 */
public class DbTableFinder<T, R> {

	private static final String TABLE_FIELD_NAME = "TABLE";

	private final Class<T> tableSuperClass;
	private final Class<R> tableRowSuperClass;

	public DbTableFinder(Class<T> tableSuperClass, Class<R> tableRowSuperClass) {

		this.tableSuperClass = Objects.requireNonNull(tableSuperClass);
		this.tableRowSuperClass = Objects.requireNonNull(tableRowSuperClass);
	}

	/**
	 * Finds all table instances in the class path.
	 *
	 * @return all table instances (never <i>null</i>)
	 */
	public Collection<T> findAllTables() {

		return ClasspathFilesMetadata//
			.getInstance()
			.getTransitivelyImplementingClasses(tableRowSuperClass)
			.stream()
			.map(this::getTableField)
			.flatMap(Optional::stream)
			.map(this::getTableInstance)
			.collect(Collectors.toList());
	}

	private Optional<Field> getTableField(Class<?> tableRowClass) {

		return ReflectionUtils.getDeclaredField(tableRowClass, TABLE_FIELD_NAME);
	}

	private T getTableInstance(Field tableField) {

		var tableInstance = ReflectionUtils.getStaticValue(tableField);
		return tableSuperClass.cast(tableInstance);
	}
}
