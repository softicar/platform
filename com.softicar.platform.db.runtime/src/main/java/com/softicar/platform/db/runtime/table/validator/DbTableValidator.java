package com.softicar.platform.db.runtime.table.validator;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Assert;

public class DbTableValidator<R> extends Assert {

	private final IDbTable<R, ?> table;
	private final List<? extends IDbField<R, ?>> dataFields;

	public DbTableValidator(IDbTable<R, ?> table) {

		this.table = table;
		this.dataFields = this.table.getDataFields();
	}

	public void validate() {

		validateStringFields();
		validateNullability();
	}

	private void validateStringFields() {

		Collection<String> errors = new ArrayList<>();

		for (IDbField<R, ?> field: dataFields) {
			if (field.getFieldType().equals(SqlFieldType.STRING)) {
				IDbStringField<R> stringField = CastUtils.cast(field);
				if (stringField.getMaximumLength() == 0 && stringField.getLengthBits() == 0) {
					String message = String//
						.format(//
							"String field %s.`%s` needs either a 'MAXLENGTH' or a 'LENGTHBITS' attribute.",
							table.getFullName(),
							field.getTitle());
					errors.add(message);
				}
			}
		}

		if (!errors.isEmpty()) {
			throw new AssertionError(Imploder.implode(errors, "\n"));
		}
	}

	private void validateNullability() {

		Collection<String> errors = new ArrayList<>();

		for (IDbField<R, ?> field: dataFields) {
			if (field.hasDefault() && field.getDefault() == null && !field.isNullable()) {
				String message = String//
					.format(//
						"Field %s.`%s` defaults to NULL but it is not NULLABLE.",
						table.getFullName(),
						field.getTitle());
				errors.add(message);
			}
		}

		if (!errors.isEmpty()) {
			throw new AssertionError(Imploder.implode(errors, "\n"));
		}
	}
}
