package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.logic.save.DbTableRowSaver;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EmfAttributesApplier<R extends IEmfTableRow<R, ?>> {

	private final R tableRow;
	private final Collection<EmfAttributeValueFrame<R, ?>> valueFrames;
	private final List<IEmfValidator<R>> additionalValidators;
	private final EmfValidationResult validationResult;

	public EmfAttributesApplier(R tableRow, Collection<EmfAttributeValueFrame<R, ?>> valueFrames) {

		this.tableRow = tableRow;
		this.valueFrames = valueFrames;
		this.additionalValidators = new ArrayList<>();
		this.validationResult = new EmfValidationResult();
	}

	public EmfAttributesApplier<R> addAdditionalValidators(List<IEmfValidator<R>> additionalValidators) {

		this.additionalValidators.addAll(additionalValidators);
		return this;
	}

	public void applyValidateAndSave() {

		try (DbTransaction transaction = new DbTransaction()) {
			reloadAttributeOwners();
			applyAttributeValues();
			saveAttributeOwners(CastUtils.cast(tableRow));
			notifyAttributeInputs();
			transaction.commit();
		}
	}

	// ------------------------------ private ------------------------------ //

	private void reloadAttributeOwners() {

		tableRow//
			.getAttributeOwners()
			.stream()
			.filter(owner -> !owner.impermanent())
			.forEach(owner -> owner.reloadForUpdate());
	}

	private void applyAttributeValues() {

		for (var valueFrame: valueFrames) {
			try {
				valueFrame.applyToTableRow();
			} catch (SofticarUserException exception) {
				validationResult.addError(valueFrame.getAttribute(), IDisplayString.create(exception.getMessage()));
			}
		}
	}

	private <S extends IEmfTableRow<S, P>, P> void saveAttributeOwners(S tableRow) {

		new DbTableRowSaver<>(tableRow.table())//
			.addRow(tableRow)
			.setFinalizingBeforeSaveHook(new ValidationHook())
			.setLazyMode(true)
			.save();
	}

	private void notifyAttributeInputs() {

		for (var valueFrame: valueFrames) {
			valueFrame.executePostSaveHook();
		}
	}

	private class ValidationHook implements INullaryVoidFunction {

		@Override
		public void apply() {

			validateUniqueKeys();
			validateAttributeOwners();
			additionalValidators.forEach(validator -> validator.validate(tableRow, validationResult));

			if (validationResult.hasErrors()) {
				throw new EmfValidationException(tableRow.table(), validationResult);
			}
		}

		private void validateUniqueKeys() {

			tableRow//
				.getAttributeOwners()
				.forEach(this::validateUniqueKeys);
		}

		private void validateUniqueKeys(IEmfTableRow<?, ?> entity) {

			new EmfUniqueKeysValidator<>(CastUtils.cast(entity), validationResult).validate();
		}

		private void validateAttributeOwners() {

			tableRow//
				.getAttributeOwners()
				.forEach(owner -> owner.validate(validationResult));
		}
	}
}
