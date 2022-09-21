package com.softicar.platform.db.runtime.table.finder;

import com.softicar.platform.common.code.classpath.metadata.IClasspathFilesMetadata;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;
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

	private final IClasspathFilesMetadata metadata;
	private final Class<T> tableSuperClass;
	private final Class<R> tableRowSuperClass;

	public DbTableFinder(IClasspathFilesMetadata metadata, Class<T> tableSuperClass, Class<R> tableRowSuperClass) {

		this.metadata = Objects.requireNonNull(metadata);
		this.tableSuperClass = Objects.requireNonNull(tableSuperClass);
		this.tableRowSuperClass = Objects.requireNonNull(tableRowSuperClass);
	}

	/**
	 * Finds all table instances in the class path.
	 *
	 * @return all table instances (never <i>null</i>)
	 */
	public Collection<T> findAllTables() {

		return metadata//
			.getTransitivelyImplementingClasses(tableRowSuperClass)
			.stream()
			.filter(this::isNotAbstract)
			.map(this::getTableInstance)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private boolean isNotAbstract(Class<?> tableRowClass) {

		return !Modifier.isAbstract(tableRowClass.getModifiers());
	}

	private T getTableInstance(Class<?> tableRowClass) {

		Field tableField = getTableField(tableRowClass);
		if (tableField != null) {
			try {
				// TODO PLAT-1090 this cast should not be necessary
				return tableSuperClass.cast(tableField.get(null));
			} catch (IllegalArgumentException | IllegalAccessException exception) {
				Log.ferror("Failed to access %s field of table row class: %s", TABLE_FIELD_NAME, tableRowClass.getCanonicalName());
				DevNull.swallow(exception);
			}
		}
		return null;
	}

	private Field getTableField(Class<?> tableRowClass) {

		try {
			return tableRowClass.getField(TABLE_FIELD_NAME);
		} catch (NoSuchFieldException exception) {
			DevNull.swallow(exception);
			if (tableRowClass.getAnnotation(TestingOnly.class) == null) {
				Log
					.fwarning(//
						"Missing %s field in table row class %s. Add annotation @%s to skip this warning.",
						TABLE_FIELD_NAME,
						tableRowClass.getCanonicalName(),
						TestingOnly.class.getSimpleName());
			}
			return null;
		}
	}
}
