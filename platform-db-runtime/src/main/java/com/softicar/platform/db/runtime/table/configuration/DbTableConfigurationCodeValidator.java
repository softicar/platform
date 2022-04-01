package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.code.classpath.iterable.ClasspathFileIterable;
import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathRootFolder;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.test.DbRandomAutoIncrementSupplier;
import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Validates the configuration of every {@link IDbTable} in this repository.
 *
 * @author Alexander Schmidt
 */
@JavaCodeValidator
public class DbTableConfigurationCodeValidator implements IJavaCodeValidator {

	private static final String INTEGRATION_PROJECT_NAME_JSON_PATH = "$.integrationProjectName";
	private static final String TABLE_PACKAGE_PREFIX_JSON_PATH = "$.tablePackagePrefix";

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		var reader = environment.getConfigurationJsonValueReader();
		JavaClasspath classPath = environment.getClassPath();
		Optional<String> integrationProjectName = reader.readValue(INTEGRATION_PROJECT_NAME_JSON_PATH);
		Optional<String> tablePackagePrefix = reader.readValue(TABLE_PACKAGE_PREFIX_JSON_PATH);

		if (isEnabled(classPath, integrationProjectName, tablePackagePrefix)) {
			try (var databaseScope = new DbDatabaseScope(createTestDatabase())) {
				for (IDbTable<?, ?> table: findAllTables(IDbTable.class, IDbTableRow.class, tablePackagePrefix.get())) {
					table.assertValidConfigurationOrThrow();
				}
			}
		}
	}

	private DbTestDatabase createTestDatabase() {

		var testDatabase = new DbTestDatabase();
		testDatabase.addListener(new DbAutomaticTableCreator(new DbRandomAutoIncrementSupplier()));
		return testDatabase;
	}

	private boolean isEnabled(JavaClasspath classPath, Optional<String> integrationProjectName, Optional<String> tablePackagePrefix) {

		return integrationProjectName.isPresent()//
				&& tablePackagePrefix.isPresent()//
				&& getClasspathDirectories(classPath).contains("/" + integrationProjectName.get() + "/");
	}

	private String getClasspathDirectories(JavaClasspath classPath) {

		return classPath//
			.getRootFolders()
			.stream()
			.map(JavaClasspathRootFolder::getFile)
			.map(File::getPath)
			.collect(Collectors.joining(","));
	}

	private <T, R> Collection<T> findAllTables(Class<T> tableClass, Class<R> tableRowClass, String tablePackagePrefix) {

		ClasspathFilesMetadata metadata = new ClasspathFilesMetadata(new ClasspathFileIterable());
		return new DbTableFinder<>(metadata, tableClass, tableRowClass)//
			.findAllTables()
			.stream()
			.filter(table -> isRelevant(table, tablePackagePrefix))
			.collect(Collectors.toList());
	}

	private <T> boolean isRelevant(T table, String tablePackagePrefix) {

		return table.getClass().getCanonicalName().startsWith(tablePackagePrefix + ".");
	}
}
