package com.softicar.platform.emf.editor;

import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.EmfValidationResult;

/**
 * Validates a given entity with respect to all its unique keys.
 * <p>
 * Effectively, this class checks if another entity with the same unique key
 * value(s) exists. The validation results are gathered in the given
 * {@link EmfValidationResult} object.
 *
 * @author Oliver Richers
 */
public class EmfUniqueKeysValidator<R extends IEmfTableRow<R, ?>> {

	private final R tableRow;
	private final EmfValidationResult validationResult;

	public EmfUniqueKeysValidator(R tableRow, EmfValidationResult validationResult) {

		this.tableRow = tableRow;
		this.validationResult = validationResult;
	}

	public void validate() {

		tableRow//
			.table()
			.getAllKeys()
			.stream()
			.filter(this::isRelevantKey)
			.forEach(this::validate);
	}

	private boolean isRelevantKey(IDbKey<R> key) {

		return key.isUniqueKey() && !key.isPrimaryKey();
	}

	private void validate(IDbKey<R> key) {

		new EmfUniqueKeyValidator<>(tableRow, key, validationResult).validate();
	}
}
