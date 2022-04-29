package com.softicar.platform.emf.table.validator;

import com.softicar.platform.common.code.java.reflection.DeclaredFieldFinder;
import com.softicar.platform.common.code.java.reflection.TypeParameterAnalyzer;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.common.testing.AssertionErrorMessageCollector;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.EmfRoleToStringVisitor;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.log.EmfDummyLogger;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.validator.fields.EmfTableDayFieldChecker;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Assert;

/**
 * FIXME This class has grown into a mess. It needs some cleanup and extraction.
 */
public class EmfTableValidator<R extends IEmfTableRow<R, ?>> extends Assert {

	private final IEmfTable<R, ?, ?> table;
	private final List<? extends IDbField<R, ?>> dataFields;

	public EmfTableValidator(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.dataFields = this.table.getDataFields();
	}

	public void validate() {

		validateScopeFields();
		validateLogging();
		assertSingleTransactionField();
		assertNoCyclicRoles();
		assertValidActiveField();
		assertNoUniqueKeysWithActiveColumn();
		assertNoUniqueKeysInLogTable();
		validateDayFields();
		validateStaticRoleFields();
	}

	private void validateScopeFields() {

		Optional<?> parentField = table.getScopeField();
		if (!parentField.isPresent()) {
			// TODO test that table is reference data (i51749)
		}
	}

	private void validateLogging() {

		if (!table.getChangeLoggers().isEmpty()) {
			validateLoggedFields();
		}
	}

	private void assertSingleTransactionField() {

		boolean transactionFieldAlreadyFound = false;
		for (IDbField<R, ?> field: dataFields) {
			String className = table.getValueClass().getSimpleName();
			if (isTransactionField(field)) {
				String error = String.format("Transaction field %s.%s is a second transaction field.", className, field);
				assertFalse(error, transactionFieldAlreadyFound);
				transactionFieldAlreadyFound = true;
			}
		}
	}

	private void assertValidActiveField() {

		Collection<IDbField<?, Boolean>> activationFields = new EmfTableActivationFieldsCollector(table).collect();
		if (activationFields.size() > 1) {
			String error = String
				.format(
					"The table '%s' has more than one active field:\n%s",
					table.getClass().getSimpleName(),
					activationFields.stream().map(IDbField::getName).collect(Collectors.joining("\n")));
			throw new AssertionError(error);
		} else if (activationFields.size() == 1) {
			IDbField<?, Boolean> field = activationFields.iterator().next();
			if (field.hasDefault() && field.getDefault() == true) {
				// all fine
			} else {
				String error = String.format("The 'active' field of table '%s' must default to true.", table.getClass().getSimpleName());
				throw new AssertionError(error);
			}
		}
	}

	private void assertNoUniqueKeysWithActiveColumn() {

		Optional<IDbField<?, Boolean>> activationField = getActivationField(table);
		if (activationField.isPresent()) {
			for (IDbKey<?> uniqueKey: table.getUniqueKeys()) {
				if (uniqueKey.getFields().contains(activationField.get())) {
					String error = String
						.format(
							"Unique key '%s' in table '%s' illegally contains the activation field of the table.",
							uniqueKey.getName(),
							table.getClass().getSimpleName());
					throw new AssertionError(error);
				}
			}
		}
	}

	private void assertNoUniqueKeysInLogTable() {

		var collector = new AssertionErrorMessageCollector();
		for (IEmfChangeLogger<R> changeLogger: table.getChangeLoggers()) {
			if (changeLogger instanceof EmfPlainChangeLogger) {
				EmfPlainChangeLogger<?, ?, ?> plainChangeLogger = EmfPlainChangeLogger.class.cast(changeLogger);
				IDbTable<?, ?> logTable = plainChangeLogger.getLogTable();
				for (IDbKey<?> uniqueKey: getAllUniqueNonPrimaryKeys(logTable)) {
					collector
						.add(//
							"Log table '%s' erroneously contains a unique key: '%s'",
							logTable.getFullName().getCanonicalName(),
							uniqueKey.getName());
				}
			}
		}
		collector.throwIfNecessary();
	}

	private void validateDayFields() {

		var collector = new AssertionErrorMessageCollector();

		EmfTableDayFieldChecker<R> checker = new EmfTableDayFieldChecker<>(dataFields);
		if (checker.hasSoleValidFromField()) {
			collector
				.add(//
					"Table %s has a `%s` field but lacks a `%s` field.",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME,
					EmfTableDayFieldChecker.VALID_TO_FIELD_NAME);
		}
		if (checker.hasSoleValidToField()) {
			collector
				.add(//
					"Table %s has a `%s` field but lacks a `%s` field.",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_TO_FIELD_NAME,
					EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME);
		}
		if (checker.hasValidFromFieldWithInvalidNullability()) {
			collector
				.add(//
					"Field %s.`%s` must not be nullable.",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME);
		}
		if (checker.hasValidToFieldWithInvalidNullability()) {
			collector
				.add(//
					"Field %s.`%s` must not be nullable.",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_TO_FIELD_NAME);
		}
		if (checker.hasValidFromFieldWithIllegalDefaultValue()) {
			collector
				.add(//
					"Field %s.`%s` has an illegal default value (expected %s, or none).",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME,
					EmfTableDayFieldChecker.EXPECTED_VALID_FROM_DEFAULT_VALUE);
		}
		if (checker.hasValidToFieldWithIllegalDefaultValue()) {
			collector
				.add(//
					"Field %s.`%s` has an illegal default value (expected %s, or none).",
					table.getFullName(),
					EmfTableDayFieldChecker.VALID_TO_FIELD_NAME,
					EmfTableDayFieldChecker.EXPECTED_VALID_TO_DEFAULT_VALUE);
		}

		collector.throwIfNecessary();
	}

	private void validateStaticRoleFields() {

		var collector = new AssertionErrorMessageCollector();
		for (Field field: new DeclaredFieldFinder(table.getClass()).findDeclaredFields()) {
			collector.addAll(validateRoleField(field, IEmfModuleRole.class));
			collector.addAll(validateRoleField(field, IEmfStaticRole.class));
		}
		collector.throwIfNecessary();
	}

	private AssertionErrorMessageCollector validateRoleField(Field roleField, Class<?> expectedRoleType) {

		var collector = new AssertionErrorMessageCollector();
		if (ReflectionUtils.isDeclaredType(roleField, expectedRoleType)) {
			if (!ReflectionUtils.isPublicStaticFinal(roleField)) {
				collector
					.add(//
						"%s field %s.%s must be 'public static final'.",
						expectedRoleType.getSimpleName(),
						table.getClass().getCanonicalName(),
						roleField.getName());
			}

			var analyzer = new TypeParameterAnalyzer(roleField);
			if (!analyzer.hasExpectedTypeParameter(table.getValueClass())) {
				collector
					.add(//
						"%s field %s.%s has an unexpected type parameter. Expected [%s], encountered [%s].",
						expectedRoleType.getSimpleName(),
						table.getClass().getCanonicalName(),
						roleField.getName(),
						table.getValueClass().getCanonicalName(),
						analyzer.getTypeParameterName());
			}
		}
		return collector;
	}

	/**
	 * This method tries to build a String for each role used in the table.<br>
	 * If there is a cyclic call, a StackOverflow should happen.<br>
	 * If nothing happens (method finishes without errors) everything is fine.
	 */
	private void assertNoCyclicRoles() {

		CurrentEmfRoleRegistry//
			.get()
			.getRoles(table)
			.forEach(role -> testRoleStringBuilder(role));
	}

	private void testRoleStringBuilder(IEmfRole<?> role) {

		new EmfRoleToStringVisitor<>(role).toString();
	}

	@SuppressWarnings("unchecked")
	private void validateLoggedFields() {

		Set<IDbField<R, ?>> loggedFields = new HashSet<>();
		for (IEmfChangeLogger<R> changeLogger: table.getChangeLoggers()) {
			if (changeLogger instanceof EmfPlainChangeLogger) {
				EmfPlainChangeLogger<?, R, ?> plainChangeLogger = (EmfPlainChangeLogger<?, R, ?>) changeLogger;
				validatePrimaryKeyOfLogTable(plainChangeLogger);
				validateLogFields(plainChangeLogger);
				validateAllLogFieldsAreUsed(plainChangeLogger);
				validateLoggedStringFieldAttributes(plainChangeLogger);
				addFields(plainChangeLogger.getLoggedFields(), loggedFields);
			} else if (changeLogger instanceof EmfDummyLogger) {
				addFields(((EmfDummyLogger<R>) changeLogger).getFields(), loggedFields);
			}
		}
		assertAllNecessaryFieldsAreLogged(loggedFields);
	}

	private void validateLoggedStringFieldAttributes(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

		plainChangeLogger//
			.getLoggedFields()
			.stream()
			.filter(loggedField -> loggedField instanceof IDbStringField)
			.forEach(loggedField -> assertSameStringAttributes(loggedField, plainChangeLogger.getLogField(loggedField).get()));
	}

	private void assertSameStringAttributes(IDbField<?, ?> loggedField, IDbField<?, ?> logField) {

		IDbStringField<?> loggedStringField = (IDbStringField<?>) loggedField;
		IDbStringField<?> logStringField = (IDbStringField<?>) logField;
		assertEquals(//
			String
				.format(
					"Maximum length of log field `%s`.`%s` is not equal to its target field.",
					logStringField.getTable().getFullName(),
					logStringField.getName()),
			loggedStringField.getMaximumLength(),
			logStringField.getMaximumLength());
		assertEquals(//
			String
				.format(
					"Lengthbits of log field `%s`.`%s` are not equal to its target field.",
					logStringField.getTable().getFullName(),
					logStringField.getName()),
			loggedStringField.getLengthBits(),
			logStringField.getLengthBits());
	}

	private void validatePrimaryKeyOfLogTable(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

		IDbTable<?, ?> logTable = plainChangeLogger.getLogTable();
		List<?> primaryKeyFields = logTable.getPrimaryKey().getFields();
		assertEquals(//
			String.format("Log table %s must have exactly two primary key columns.", logTable.getValueClass().getSimpleName()),
			2,
			primaryKeyFields.size());
		assertSame(//
			String.format("First primary key column of log table %s must be the table row field.", logTable.getValueClass().getSimpleName()),
			plainChangeLogger.getTableRowField(),
			primaryKeyFields.get(0));
		assertSame(//
			String.format("Second primary key column of log table %s must be the transaction field.", logTable.getValueClass().getSimpleName()),
			plainChangeLogger.getTransactionField(),
			primaryKeyFields.get(1));
	}

	private void validateLogFields(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

		plainChangeLogger//
			.getLoggedFields()
			.forEach(field -> validateLogField(field, plainChangeLogger.getLogField(field).get()));
	}

	private void validateAllLogFieldsAreUsed(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

		Set<IDbField<?, ?>> usedLogFields = plainChangeLogger//
			.getLoggedFields()
			.stream()
			.map(plainChangeLogger::getLogField)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toSet());

		plainChangeLogger//
			.getLogTable()
			.getDataFields()
			.forEach(
				logField -> assertTrue(//
					String.format("Unused log field: %s.%s", logField.getTable().getValueClass().getSimpleName(), logField.getName()),
					usedLogFields.contains(logField)));
	}

	private void validateLogField(IDbField<?, ?> field, IDbField<?, ?> logField) {

		assertEquals(//
			String.format("Value class of log field %s.%s", logField.getTable().getValueClass().getSimpleName(), logField.getName()),
			field.getValueType().getValueClass(),
			logField.getValueType().getValueClass());
		if (field.isNullable()) {
			assertTrue(//
				String.format("Nullability of log field %s.%s", logField.getTable().getValueClass().getSimpleName(), logField.getName()),
				logField.isNullable());
		}
		assertFalse(//
			String.format("Log field %s.%s may not have a non-null default value.", logField.getTable().getValueClass().getSimpleName(), logField.getName()),
			hasNonNullDefaultValue(logField));
	}

	private void addFields(Collection<IDbField<R, ?>> fieldsToLog, Set<IDbField<R, ?>> loggedFields) {

		fieldsToLog
			.stream()
			.forEach(
				field -> assertTrue(//
					String.format("Field %s.%s is logged multiple times.", table.getValueClass().getSimpleName(), field),
					loggedFields.add(field)));
	}

	private void assertAllNecessaryFieldsAreLogged(Set<IDbField<R, ?>> loggedFields) {

		for (IDbField<R, ?> field: dataFields) {
			String className = table.getValueClass().getSimpleName();
			if (isTransactionField(field)) {
				assertFalse(//
					String.format("Transaction field %s.%s is logged.", className, field),
					loggedFields.contains(field));
			} else if (isImmutable(field)) {
				assertFalse(//
					String.format("Immutable field %s.%s is logged.", className, field),
					loggedFields.contains(field));
			} else {
				assertTrue(//
					String.format("Field %s.%s is not being logged.", className, field),
					loggedFields.contains(field));
			}
		}
	}

	private boolean isImmutable(IDbField<R, ?> field) {

		return table.getAttribute(field).isImmutable();
	}

	private boolean isTransactionField(IDbField<R, ?> field) {

		return IEmfTransactionObject.class.isAssignableFrom(field.getValueType().getValueClass());
	}

	private boolean hasNonNullDefaultValue(IDbField<?, ?> logField) {

		return logField.hasDefault() && logField.getDefault() != null;
	}

	private static Optional<IDbField<?, Boolean>> getActivationField(IEmfTable<?, ?, ?> table) {

		return new EmfTableActivationFieldsCollector(table)//
			.collect()
			.stream()
			.findFirst();
	}

	private static <E> List<? extends IDbKey<E>> getAllUniqueNonPrimaryKeys(IDbTable<E, ?> table) {

		return table//
			.getUniqueKeys()
			.stream()
			.filter(key -> !key.isPrimaryKey())
			.collect(Collectors.toList());
	}
}
