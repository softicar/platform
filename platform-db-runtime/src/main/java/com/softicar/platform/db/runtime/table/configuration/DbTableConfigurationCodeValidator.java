package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.testing.AssertionErrorMessageCollector;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.runtime.test.DbRandomAutoIncrementSupplier;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validates the configuration of every {@link IDbTable} in this repository.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@JavaCodeValidator
public class DbTableConfigurationCodeValidator implements IJavaCodeValidator {

	private static final Set<Class<?>> DEFAULT_TABLE_CLASSES = Set.of(DbEnumTable.class, DbObjectTable.class, DbRecordTable.class);

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		try (var databaseScope = new DbDatabaseScope(createTestDatabase())) {
			var errors = new AssertionErrorMessageCollector();
			for (IDbTable<?, ?> table: findCustomTables()) {
				environment.logVerbose("Validating class: %s", table.getClass().getCanonicalName());
				errors.addAll(table.validateConfiguration());
			}
			errors.throwIfNecessary();
		}
	}

	private DbTestDatabase createTestDatabase() {

		var testDatabase = new DbTestDatabase();
		testDatabase.addListener(new DbAutomaticTableCreator(new DbRandomAutoIncrementSupplier()));
		return testDatabase;
	}

	private Collection<IDbTable<?, ?>> findCustomTables() {

		return new DbTableFinder()//
			.findAllTables()
			.stream()
			.filter(this::isCustomTableClass)
			.collect(Collectors.toList());
	}

	private boolean isCustomTableClass(IDbTable<?, ?> table) {

		return !DEFAULT_TABLE_CLASSES.contains(table.getClass());
	}
}
