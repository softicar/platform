package com.softicar.platform.db.runtime.table.validator;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.testing.AssertionErrorMessageCollector;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Assert;

public class DbTableValidator<R> extends Assert implements IDbTableValidator {

	private final IDbTable<R, ?> table;
	private final List<? extends IDbField<R, ?>> dataFields;
	private final Collection<? extends IDbKey<R>> allKeys;
	private final AssertionErrorMessageCollector errors;

	public DbTableValidator(IDbTable<R, ?> table) {

		this.table = table;
		this.dataFields = table.getDataFields();
		this.allKeys = table.getAllKeys();
		this.errors = new AssertionErrorMessageCollector();
	}

	@Override
	public AssertionErrorMessageCollector validate() {

		validateStringFields();
		validateNullability();
		validatePrimaryKey();
		validateForeignKeys();

		return errors;
	}

	private void validateStringFields() {

		for (IDbField<R, ?> field: dataFields) {
			if (field.getFieldType().equals(SqlFieldType.STRING)) {
				IDbStringField<R> stringField = CastUtils.cast(field);
				if (stringField.getMaximumLength() == 0 && stringField.getLengthBits() == 0) {
					errors
						.add(//
							"String field %s.`%s` needs either a 'MAXLENGTH' or a 'LENGTHBITS' attribute.",
							table.getFullName(),
							field.getName());
				}
			}
		}
	}

	private void validateNullability() {

		for (IDbField<R, ?> field: dataFields) {
			if (field.hasDefault() && field.getDefault() == null && !field.isNullable()) {
				errors
					.add(//
						"Field %s.`%s` defaults to NULL but it is not NULLABLE.",
						table.getFullName(),
						field.getName());
			}

			if (field.isNullable() && !field.hasDefault()) {
				errors
					.add(//
						"Field %s.`%s` is NULLABLE but does not have a default value.",
						table.getFullName(),
						field.getName());
			}
		}
	}

	private void validatePrimaryKey() {

		var primaryKey = table.getPrimaryKey();
		if (primaryKey.isBase()) {
			var field = primaryKey.getFields().get(0);
			if (!field.getComment().orElse("").equals("@base@")) {
				errors
					.add(//
						"Primary-key base field %s.`%s` lacks the comment '@base@'.",
						table.getFullName(),
						field.getName());
			}
		}
	}

	private void validateForeignKeys() {

		var leadingIndexFields = allKeys//
			.stream()
			.map(key -> key.getFields().get(0))
			.collect(Collectors.toCollection(() -> new HashSet<>()));

		int foreignKeyCounter = 0;
		for (IDbField<R, ?> field: table.getAllFields()) {
			if (IDbForeignRowField.class.isInstance(field)) {
				++foreignKeyCounter;
				var foreignRowField = (IDbForeignRowField<R, ?, ?>) field;
				Optional<String> foreignKeyName = foreignRowField.getForeignKeyName();
				if (!foreignKeyName.isPresent()) {
					errors
						.add(//
							"Foreign-key field %s.`%s` does not have a constraint name.",
							table.getFullName(),
							field.getName());
				} else {
					String expectedConstraintPrefix = table.getFullName().getSimpleName() + "_ibfk_" + foreignKeyCounter;
					if (!foreignKeyName.get().startsWith(expectedConstraintPrefix)) {
						errors
							.add(//
								"The foreign-key constraint on %s.`%s` should be called `%s` but it is called `%s`.",
								table.getFullName(),
								field.getName(),
								expectedConstraintPrefix,
								foreignKeyName.get());
					}
				}

				if (!leadingIndexFields.contains(field)) {
					errors
						.add(//
							"Foreign-key field %s.`%s` does not occur as the first column in any key.",
							table.getFullName(),
							field.getName());
				}
			}
		}
	}
}
