package com.softicar.platform.db.runtime.table.validator;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;

public class DbTableValidator<R> extends Assert {

	private final IDbTable<R, ?> table;
	private final List<? extends IDbField<R, ?>> dataFields;
	private final Collection<? extends IDbKey<R>> allKeys;
	private final ErrorList errors;

	public DbTableValidator(IDbTable<R, ?> table) {

		this.table = table;
		this.dataFields = table.getDataFields();
		this.allKeys = table.getAllKeys();
		this.errors = new ErrorList();
	}

	public void validate() {

		validateStringFields();
		validateNullability();
		validatePrimaryKey();
		validateForeignKeys();

		if (!errors.isEmpty()) {
			throw new AssertionError(Imploder.implode(errors, "\n"));
		}
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

		for (IDbField<R, ?> field: dataFields) {
			CastUtils.tryCast(field, IDbForeignRowField.class).ifPresent(foreignRowField -> {
				if (!foreignRowField.getForeignKeyName().isPresent()) {
					errors
						.add(//
							"Foreign-key field %s.`%s` does not have a constraint name.",
							table.getFullName(),
							field.getName());
				}

				if (!leadingIndexFields.contains(field)) {
					errors
						.add(//
							"Foreign-key field %s.`%s` does not occur as the first column in any key.",
							table.getFullName(),
							field.getName());
				}
			});
		}
	}

	private static class ErrorList extends ArrayList<String> {

		public void add(String format, Object...args) {

			add(format.formatted(args));
		}
	}
}
