package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.code.classpath.iterable.ClasspathFileIterable;
import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;

/**
 * Validates the configuration of every {@link IDbTable}.
 *
 * @author Alexander Schmidt
 */
@JavaCodeValidator
public class DbTableConfigurationCodeValidator implements IJavaCodeValidator {

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		for (IDbTable<?, ?> table: findAllTables(IDbTable.class, IDbTableRow.class)) {
			table.assertValidConfigurationOrThrow();
		}
	}

	private <T, R> Collection<T> findAllTables(Class<T> tableClass, Class<R> tableRowClass) {

		ClasspathFilesMetadata metadata = new ClasspathFilesMetadata(new ClasspathFileIterable());
		return new DbTableFinder<>(metadata, tableClass, tableRowClass).findAllTables();
	}
}
