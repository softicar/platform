package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import com.softicar.platform.db.runtime.test.DbRandomAutoIncrementSupplier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Validates the configuration of every {@link IDbTable} in this repository.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@JavaCodeValidator
public class DbTableConfigurationCodeValidator implements IJavaCodeValidator {

	private static final String BUILDER_FIELD_NAME = "BUILDER";
	private static final String TABLE_FIELD_NAME = "TABLE";
	private static final String TABLE_PACKAGE_PREFIX_JSON_PATH = "$.tablePackagePrefix";
	private static final Set<Class<?>> DEFAULT_TABLE_CLASSES = Set.of(DbEnumTable.class, DbObjectTable.class, DbRecordTable.class);
	private String tablePackagePrefix;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.tablePackagePrefix = environment//
			.getConfigurationJsonValueReader()
			.readValue(TABLE_PACKAGE_PREFIX_JSON_PATH)
			.orElse("");

		try (var databaseScope = new DbDatabaseScope(createTestDatabase())) {
			for (IDbTable<?, ?> table: findAllTables()) {
				environment.logVerbose("Validating class: %s", table.getClass().getCanonicalName());
				table.assertValidConfigurationOrThrow();
			}
		}
	}

	private DbTestDatabase createTestDatabase() {

		var testDatabase = new DbTestDatabase();
		testDatabase.addListener(new DbAutomaticTableCreator(new DbRandomAutoIncrementSupplier()));
		return testDatabase;
	}

	private Collection<IDbTable<?, ?>> findAllTables() {

		var tables = new ArrayList<IDbTable<?, ?>>();
		for (var root: JavaClasspath.getInstance().getPayloadRoots()) {
			for (var javaClass: root.getAnalyzedClasses()) {
				findTable(javaClass).ifPresent(tables::add);
			}
		}
		return tables;
	}

	private Optional<IDbTable<?, ?>> findTable(AnalyzedJavaClass javaClass) {

		if (hasBuilderField(javaClass) && hasTableField(javaClass)) {
			var tableField = ReflectionUtils.getDeclaredFieldOrThrow(javaClass.loadClass(), TABLE_FIELD_NAME);
			if (ReflectionUtils.isPublicStaticFinal(tableField)) {
				var table = ReflectionUtils.getStaticValue(tableField);
				if (isTableInstance(table) && isCustomTableClass(table.getClass()) && isMatchingPackagePrefix(table.getClass())) {
					return Optional.of((IDbTable<?, ?>) table);
				}
			}
		}
		return Optional.empty();
	}

	private boolean hasBuilderField(AnalyzedJavaClass javaClass) {

		return javaClass.findDeclaredField(BUILDER_FIELD_NAME).isPresent();
	}

	private boolean hasTableField(AnalyzedJavaClass javaClass) {

		return javaClass.findDeclaredField(TABLE_FIELD_NAME).isPresent();
	}

	private boolean isTableInstance(Object table) {

		return IDbTable.class.isInstance(table);
	}

	private boolean isCustomTableClass(Class<?> tableClass) {

		return !DEFAULT_TABLE_CLASSES.contains(tableClass);
	}

	private boolean isMatchingPackagePrefix(Class<?> tableClass) {

		return tableClass.getPackageName().startsWith(tablePackagePrefix);
	}
}
