package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.code.classpath.iterable.ClasspathFileIterable;
import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.test.DbRandomAutoIncrementSupplier;
import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Validates the configuration of every {@link IDbTable} in this repository.
 * <p>
 * FIXME This is too inefficient. The same tables are checked over and over
 * again. (PLAT-754).
 *
 * @author Alexander Schmidt
 */
//@JavaCodeValidator // FIXME Fix and re-enable this. (PLAT-754)
public class DbTableConfigurationCodeValidator implements IJavaCodeValidator {

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		try (var databaseScope = new DbDatabaseScope(createTestDatabase())) {
			for (IDbTable<?, ?> table: findAllTables(IDbTable.class, IDbTableRow.class)) {
				table.assertValidConfigurationOrThrow();
			}
		}
	}

	private DbTestDatabase createTestDatabase() {

		var testDatabase = new DbTestDatabase();
		testDatabase.addListener(new DbAutomaticTableCreator(new DbRandomAutoIncrementSupplier()));
		return testDatabase;
	}

	private <T, R> Collection<T> findAllTables(Class<T> tableClass, Class<R> tableRowClass) {

		ClasspathFilesMetadata metadata = new ClasspathFilesMetadata(new ClasspathFileIterable().setJarFileFilter(isRelevantJarFile()));
		return new DbTableFinder<>(metadata, tableClass, tableRowClass).findAllTables();
	}

	private Predicate<File> isRelevantJarFile() {

		// TODO Find a better solution to determine the JAR file that contains IDbTable and IDbTableRow.
		return jarFile -> jarFile.getName().equals("platform-db-runtime.jar");
	}
}
