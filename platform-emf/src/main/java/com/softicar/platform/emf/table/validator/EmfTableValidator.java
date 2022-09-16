package com.softicar.platform.emf.table.validator;

import com.softicar.platform.common.code.java.reflection.DeclaredFieldFinder;
import com.softicar.platform.common.code.java.reflection.TypeParameterAnalyzer;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.common.testing.AssertionErrorMessageCollector;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.validator.IDbTableValidator;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import com.softicar.platform.emf.log.EmfDummyLogger;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.EmfPermissionToStringVisitor;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.validator.fields.EmfTableDayFieldChecker;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Assert;

public class EmfTableValidator<R extends IEmfTableRow<R, ?>> extends Assert implements IDbTableValidator {

	private final IEmfTable<R, ?, ?> table;
	private final AssertionErrorMessageCollector errors;

	public EmfTableValidator(IEmfTable<R, ?, ?> table) {

		this.table = Objects.requireNonNull(table);
		this.errors = new AssertionErrorMessageCollector();
	}

	@Override
	public AssertionErrorMessageCollector validate() {

		new ActiveFieldValidator<>(table, errors)//
			.validateActiveField();

		new DayFieldsValidator<>(table, errors)//
			.validateDayFields();

		new KeyValidator<>(table, errors)//
			.validateNoUniqueKeysWithActiveColumn()
			.validateNoUniqueKeysInLogTable();

		new LogTableValidator<>(table, errors)//
			.validateLogTable();

		new PermissionValidator<>(table, errors)//
			.validateNoCyclicPermissions()
			.validatePermissionFields();

		new ScopeFieldValidator<>(table, errors)//
			.validateScopeField();

		new TransactionFieldValidator<>(table, errors)//
			.validateTransactionField();

		return errors;
	}

	private static class ActiveFieldValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public ActiveFieldValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		public void validateActiveField() {

			var activationFields = new EmfTableActivationFieldsCollector(table).collect();
			if (activationFields.size() > 1) {
				errors
					.add(
						"Table %s has more than one 'active' field: %s",
						table.getFullName(),
						activationFields.stream().map(IDbField::getName).collect(Collectors.joining(", ")));
			} else if (activationFields.size() == 1) {
				IDbField<?, Boolean> field = activationFields.iterator().next();
				if (field.getDefault() != true) {
					errors.add("The 'active' field of table %s must default to TRUE.", table.getFullName());
				}
			}
		}
	}

	private static class DayFieldsValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public DayFieldsValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		public void validateDayFields() {

			EmfTableDayFieldChecker<R> checker = new EmfTableDayFieldChecker<>(table.getDataFields());
			if (checker.hasSoleValidFromField()) {
				errors
					.add(//
						"Table %s has a `%s` field but lacks a `%s` field.",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME,
						EmfTableDayFieldChecker.VALID_TO_FIELD_NAME);
			}
			if (checker.hasSoleValidToField()) {
				errors
					.add(//
						"Table %s has a `%s` field but lacks a `%s` field.",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_TO_FIELD_NAME,
						EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME);
			}
			if (checker.hasValidFromFieldWithInvalidNullability()) {
				errors
					.add(//
						"Field %s.`%s` must not be nullable.",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME);
			}
			if (checker.hasValidToFieldWithInvalidNullability()) {
				errors
					.add(//
						"Field %s.`%s` must not be nullable.",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_TO_FIELD_NAME);
			}
			if (checker.hasValidFromFieldWithIllegalDefaultValue()) {
				errors
					.add(//
						"Field %s.`%s` has an illegal default value (expected '%s', or none).",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_FROM_FIELD_NAME,
						EmfTableDayFieldChecker.EXPECTED_VALID_FROM_DEFAULT_VALUE);
			}
			if (checker.hasValidToFieldWithIllegalDefaultValue()) {
				errors
					.add(//
						"Field %s.`%s` has an illegal default value (expected '%s', or none).",
						table.getFullName(),
						EmfTableDayFieldChecker.VALID_TO_FIELD_NAME,
						EmfTableDayFieldChecker.EXPECTED_VALID_TO_DEFAULT_VALUE);
			}
		}
	}

	private static class KeyValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public KeyValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		public KeyValidator<R> validateNoUniqueKeysWithActiveColumn() {

			Optional<IDbField<?, Boolean>> activationField = getActivationField(table);
			if (activationField.isPresent()) {
				for (IDbKey<?> uniqueKey: table.getUniqueKeys()) {
					if (uniqueKey.getFields().contains(activationField.get())) {
						errors
							.add(//
								"Unique key `%s` in table %s unexpectedly comprises the 'active' field.",
								uniqueKey.getName(),
								table.getFullName());
					}
				}
			}

			return this;
		}

		public KeyValidator<R> validateNoUniqueKeysInLogTable() {

			for (IEmfChangeLogger<R> changeLogger: table.getChangeLoggers()) {
				if (changeLogger instanceof EmfPlainChangeLogger) {
					EmfPlainChangeLogger<?, ?, ?> plainChangeLogger = EmfPlainChangeLogger.class.cast(changeLogger);
					IDbTable<?, ?> logTable = plainChangeLogger.getLogTable();
					for (IDbKey<?> uniqueKey: getAllUniqueNonPrimaryKeys(logTable)) {
						errors
							.add(//
								"Log table %s unexpectedly contains a unique key: `%s`",
								logTable.getFullName(),
								uniqueKey.getName());
					}
				}
			}

			return this;
		}

		private Optional<IDbField<?, Boolean>> getActivationField(IEmfTable<?, ?, ?> table) {

			return new EmfTableActivationFieldsCollector(table)//
				.collect()
				.stream()
				.findFirst();
		}

		private <E> List<? extends IDbKey<E>> getAllUniqueNonPrimaryKeys(IDbTable<E, ?> table) {

			return table//
				.getUniqueKeys()
				.stream()
				.filter(key -> !key.isPrimaryKey())
				.collect(Collectors.toList());
		}
	}

	private static class LogTableValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public LogTableValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		public void validateLogTable() {

			if (!table.getChangeLoggers().isEmpty()) {
				Set<IDbField<R, ?>> loggedFields = new HashSet<>();
				for (IEmfChangeLogger<R> changeLogger: table.getChangeLoggers()) {
					if (changeLogger instanceof EmfPlainChangeLogger) {
						var plainChangeLogger = (EmfPlainChangeLogger<?, R, ?>) changeLogger;
						validateLogTablePimaryKey(plainChangeLogger);
						validateLogTableFields(plainChangeLogger);
						validateLogTableFieldsAreAllUsed(plainChangeLogger);
						validateLoggedStringFieldAttributes(plainChangeLogger);
						addFields(plainChangeLogger.getLoggedFields(), loggedFields);
					} else if (changeLogger instanceof EmfDummyLogger) {
						addFields(((EmfDummyLogger<R>) changeLogger).getFields(), loggedFields);
					}
				}
				validateAllNecessaryFieldsAreLogged(loggedFields);
			}
		}

		private void validateLogTablePimaryKey(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

			var logTable = plainChangeLogger.getLogTable();
			var primaryKeyFields = logTable.getPrimaryKey().getFields();

			if (primaryKeyFields.size() != 2) {
				errors.add("Log table %s: The primary key must have exactly two columns.", logTable.getFullName());
			}

			// validate table row field

			var tableRowField = primaryKeyFields.get(0);
			if (!IDbForeignRowField.class.isInstance(tableRowField)) {
				errors.add("Log table %s: The table row field in the primary key must be a foreign key field.", logTable.getFullName());
			}

			var tableRowForeignRowField = (IDbForeignRowField<?, ?, ?>) tableRowField;
			if (plainChangeLogger.getTableRowField() != tableRowForeignRowField) {
				errors.add("Log table %s: The first primary key column must be the table row field.", logTable.getFullName());
			}

			if (tableRowForeignRowField.getOnDelete() != DbForeignKeyAction.CASCADE) {
				errors.add("Log table %s: The foreign key on the table row field of the primary key must have 'ON DELETE CASCADE'.", logTable.getFullName());
			}

			if (tableRowForeignRowField.getOnUpdate() != DbForeignKeyAction.CASCADE) {
				errors.add("Log table %s: The foreign key on the table row field of the primary key must have 'ON UPDATE CASCADE'.", logTable.getFullName());
			}

			// validate transaction field

			var transactionField = primaryKeyFields.get(1);
			if (!IDbForeignRowField.class.isInstance(transactionField)) {
				errors.add("Log table %s: The transaction field in the primary key must be a foreign key field.", logTable.getFullName());
			}

			var transactionForeignRowField = (IDbForeignRowField<?, ?, ?>) transactionField;
			if (plainChangeLogger.getTransactionField() != transactionForeignRowField) {
				errors.add("Log table %s: The second primary key column must be the transaction field.", logTable.getFullName());
			}

			if (transactionForeignRowField.getOnDelete() != DbForeignKeyAction.CASCADE) {
				errors.add("Log table %s: The foreign key on the transaction field of the primary key must have 'ON DELETE CASCADE'.", logTable.getFullName());
			}

			if (transactionForeignRowField.getOnUpdate() != DbForeignKeyAction.CASCADE) {
				errors.add("Log table %s: The foreign key on the transaction field of the primary key must have 'ON UPDATE CASCADE'.", logTable.getFullName());
			}
		}

		private void validateLogTableFields(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

			plainChangeLogger//
				.getLoggedFields()
				.forEach(field -> validateLogField(field, plainChangeLogger.getLogField(field).get()));
		}

		private void validateLogTableFieldsAreAllUsed(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

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
				.stream()
				.filter(logField -> !usedLogFields.contains(logField))
				.forEach(logField -> {
					errors.add("Log field %s.`%s` is not used.", logField.getTable().getFullName(), logField.getName());
				});
		}

		private void validateLoggedStringFieldAttributes(EmfPlainChangeLogger<?, R, ?> plainChangeLogger) {

			plainChangeLogger//
				.getLoggedFields()
				.stream()
				.filter(loggedField -> loggedField instanceof IDbStringField)
				.forEach(loggedField -> validateSameStringAttributes(loggedField, plainChangeLogger.getLogField(loggedField).get()));
		}

		private void validateLogField(IDbField<?, ?> field, IDbField<?, ?> logField) {

			if (field.getValueType().getValueClass() != logField.getValueType().getValueClass()) {
				errors
					.add(
						"The value class of log field %s.`%s` does not match the value class of the original field.",
						logField.getTable().getFullName(),
						logField.getName());
			}

			if (!logField.isNullable()) {
				errors.add("Nullability of log field %s.`%s`", logField.getTable().getFullName(), logField.getName());
			}

			if (hasNonNullDefaultValue(logField)) {
				errors.add("Log field %s.`%s` must not have a non-NULL default value.", logField.getTable().getFullName(), logField.getName());
			}
		}

		private void validateAllNecessaryFieldsAreLogged(Set<IDbField<R, ?>> loggedFields) {

			for (IDbField<R, ?> field: table.getDataFields()) {
				var tableName = table.getFullName();
				if (isTransactionField(field)) {
					if (loggedFields.contains(field)) {
						errors.add("Transaction field %s.`%s` must not be logged.", tableName, field);
					}
				} else if (isImmutable(field)) {
					if (loggedFields.contains(field)) {
						errors.add("Immutable field %s.`%s` must not be logged.", tableName, field);
					}
				} else {
					if (!loggedFields.contains(field)) {
						errors.add("Field %s.`%s` must be logged.", tableName, field);
					}
				}
			}
		}

		private void validateSameStringAttributes(IDbField<?, ?> loggedField, IDbField<?, ?> logField) {

			var loggedStringField = (IDbStringField<?>) loggedField;
			var logStringField = (IDbStringField<?>) logField;

			if (loggedStringField.getMaximumLength() != logStringField.getMaximumLength()) {
				errors
					.add(//
						"The maximum length of log field %s.`%s` does not match the maximum length of the original field.",
						logStringField.getTable().getFullName(),
						logStringField.getName());
			}

			if (loggedStringField.getLengthBits() != logStringField.getLengthBits()) {
				errors
					.add(//
						"The lengthbits of log field %s.`%s` do not match the lengthbits of the original field.",
						logStringField.getTable().getFullName(),
						logStringField.getName());
			}
		}

		private void addFields(Collection<IDbField<R, ?>> fieldsToLog, Set<IDbField<R, ?>> loggedFields) {

			fieldsToLog.stream().forEach(field -> {
				if (!loggedFields.add(field)) {
					errors.add("Field %s.`%s` is logged multiple times.", table.getFullName(), field);
				}
			});
		}

		private boolean hasNonNullDefaultValue(IDbField<?, ?> logField) {

			return logField.hasDefault() && logField.getDefault() != null;
		}

		private boolean isImmutable(IDbField<R, ?> field) {

			return table.getAttribute(field).isImmutable();
		}
	}

	private static class PermissionValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public PermissionValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		/**
		 * This method tries to build a String for each {@link IEmfPermission}
		 * used in the table.
		 * <p>
		 * If there is a cyclic call, a StackOverflow should happen. If nothing
		 * happens (method finishes without errors) everything is fine.
		 */
		public PermissionValidator<R> validateNoCyclicPermissions() {

			try {
				CurrentEmfPermissionRegistry//
					.get()
					.getPermissions(table)
					.forEach(permission -> new EmfPermissionToStringVisitor<>(permission).toString());
			} catch (StackOverflowError error) {
				errors.add("Cyclic permission detected:\n%s", StackTraceFormatting.getStackTraceAsString(error));
			}
			return this;
		}

		public PermissionValidator<R> validatePermissionFields() {

			for (Field field: new DeclaredFieldFinder(table.getClass()).findDeclaredFields()) {
				validatePermissionField(field, IEmfModulePermission.class);
				validatePermissionField(field, IEmfStaticPermission.class);
			}
			return this;
		}

		private PermissionValidator<R> validatePermissionField(Field permissionField, Class<?> expectedPermissionType) {

			if (ReflectionUtils.isDeclaredType(permissionField, expectedPermissionType)) {
				if (!ReflectionUtils.isPublicStaticFinal(permissionField)) {
					errors
						.add(//
							"%s field %s.`%s` must be 'public static final'.",
							expectedPermissionType.getSimpleName(),
							table.getFullName(),
							permissionField.getName());
				}

				var analyzer = new TypeParameterAnalyzer(permissionField);
				if (!analyzer.hasExpectedTypeParameter(table.getValueClass())) {
					errors
						.add(//
							"%s field %s.`%s` has an unexpected type parameter. Expected '%s', encountered '%s'.",
							expectedPermissionType.getSimpleName(),
							table.getFullName(),
							permissionField.getName(),
							table.getValueClass().getCanonicalName(),
							analyzer.getTypeParameterName());
				}
			}
			return this;
		}
	}

	private static class ScopeFieldValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;

		public ScopeFieldValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;

			// TODO PLAT-1091 add errors if detected
			DevNull.swallow(errors);
		}

		public void validateScopeField() {

			Optional<?> parentField = table.getScopeField();
			if (!parentField.isPresent()) {
				// TODO PLAT-1091 test that table is static reference data, unless it's in CoreModule (which is not visible here)
			}
		}
	}

	private static class TransactionFieldValidator<R extends IEmfTableRow<R, ?>> {

		private final IEmfTable<R, ?, ?> table;
		private final AssertionErrorMessageCollector errors;

		public TransactionFieldValidator(IEmfTable<R, ?, ?> table, AssertionErrorMessageCollector errors) {

			this.table = table;
			this.errors = errors;
		}

		public void validateTransactionField() {

			if (getTransactionFields(table).size() > 1) {
				errors.add("Table %s must not have multiple transaction fields.", table.getFullName());
			}
		}

		private Collection<IDbField<R, ?>> getTransactionFields(IEmfTable<R, ?, ?> table) {

			return table//
				.getDataFields()
				.stream()
				.filter(field -> isTransactionField(field))
				.collect(Collectors.toList());
		}
	}

	private static <R extends IEmfTableRow<R, ?>> boolean isTransactionField(IDbField<R, ?> field) {

		return IEmfTransactionObject.class.isAssignableFrom(field.getValueType().getValueClass());
	}
}
