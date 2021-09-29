package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.EmfValidationResult;

/**
 * Validates a given entity with respect to a given unique key.
 * <p>
 * Effectively, this class checks if another entity with the same unique key
 * value(s) exists. The validation results are gathered in the given
 * {@link EmfValidationResult} object.
 *
 * @author Oliver Richers
 */
public class EmfUniqueKeyValidator<R extends IEmfTableRow<R, ?>> {

	private final R tableRow;
	private final IDbKey<R> uniqueKey;
	private final EmfValidationResult validationResult;

	/**
	 * Constructs this validator for the given {@link IEmfTableRow} and the
	 * unique key.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} to validate (never null)
	 * @param uniqueKey
	 *            the unique key to check for (never null)
	 * @param validationResult
	 *            the validation result output object (never null)
	 */
	public EmfUniqueKeyValidator(R tableRow, IDbKey<R> uniqueKey, EmfValidationResult validationResult) {

		this.tableRow = tableRow;
		this.uniqueKey = uniqueKey;
		this.validationResult = validationResult;
	}

	// TODO we should lock the selected rows to avoid race-conditions (i67717)
	public void validate() {

		if (!haveFieldsWithNullValue()) {
			ISqlSelect<R> select = tableRow//
				.table()
				.createSelect();
			for (IDbField<R, ?> field: uniqueKey.getFields()) {
				addWhereCondition(select, field);
			}
			select//
				.getFirstAsOptional()
				.filter(this::isOtherEntity)
				.ifPresent(this::addError);
		}
	}

	private boolean haveFieldsWithNullValue() {

		return uniqueKey//
			.getFields()
			.stream()
			.anyMatch(field -> field.getValue(tableRow) == null);
	}

	private <V> void addWhereCondition(ISqlSelect<R> select, IDbField<R, V> field) {

		select.where(field.isEqual(field.getValue(tableRow)));
	}

	private boolean isOtherEntity(R conflictCandidate) {

		return conflictCandidate != tableRow;
	}

	private void addError(R conflictingEntity) {

		for (IDbField<R, ?> field: uniqueKey.getFields()) {
			validationResult
				.addError(//
					getAttribute(field),
					getErrorMessage(conflictingEntity));
		}
	}

	private IEmfAttribute<R, ?> getAttribute(IDbField<R, ?> field) {

		return tableRow.table().getAttribute(field);
	}

	private IDisplayString getErrorMessage(R conflictingEntity) {

		return EmfI18n.BUSINESS_KEY_CONFLICT_WITH_AN_EXISTING_ENTRY_ARG1.toDisplay(conflictingEntity.toDisplay());
	}
}
