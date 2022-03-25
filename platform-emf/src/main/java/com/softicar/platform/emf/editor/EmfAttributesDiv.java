package com.softicar.platform.emf.editor;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A table to view and edit the attributes on an {@link IEmfTableRow}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfAttributesDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final R tableRow;
	private final List<EmfAttributeValueInputFrame<R, ?>> inputRows;
	private final EmfAttributeInputRowDependencyController<R> constraintController;
	private final List<IEmfValidator<R>> additionalValidators;
	private EmfAttributesDivValidationDiv<R> validationDiv;
	private DomLabelGrid attributeGrid;

	public EmfAttributesDiv(R tableRow) {

		this.tableRow = tableRow;
		this.inputRows = new ArrayList<>();
		this.constraintController = new EmfAttributeInputRowDependencyController<>(tableRow.table());
		this.additionalValidators = new ArrayList<>();
		this.validationDiv = null;
		this.attributeGrid = appendChild(new DomLabelGrid());
	}

	protected void removeContent() {

		removeChildren();
		this.attributeGrid = appendChild(new DomLabelGrid());
	}

	// ------------------------------ display rows ------------------------------ //

	public void appendDisplayRow(IDbField<R, ?> field) {

		appendDisplayRow(tableRow.table().getAttribute(field));
	}

	public void appendDisplayRow(IEmfAttribute<R, ?> attribute) {

		attributeGrid.add(attribute.getTitle(), new EmfAttributeValueDisplayFrame<>(attribute, tableRow));
	}

	// ------------------------------ input rows ------------------------------ //

	public List<EmfAttributeValueInputFrame<R, ?>> getInputRows() {

		return inputRows;
	}

	public <V> void appendInputRow(IDbField<R, V> field) {

		appendInputRow(tableRow.table().getAttribute(field));
	}

	public <V> void appendInputRow(IEmfAttribute<R, V> attribute) {

		Optional<IEmfInput<V>> input = attribute.createInput(tableRow);
		if (input.isPresent()) {
			EmfAttributeValueInputFrame<R, V> inputFrame = new EmfAttributeValueInputFrame<>(attribute, tableRow, input.get());
			inputRows.add(inputFrame);
			constraintController.addInputRow(attribute, inputFrame);
			attributeGrid.add(new EmfAttributeValueLabel<>(attribute, tableRow), inputFrame);
		} else {
			appendDisplayRow(attribute);
		}
	}

	// ------------------------------ apply and save ------------------------------ //

	public void applyFromEntity() {

		inputRows.forEach(row -> row.applyFromTableRow());
	}

	/**
	 * Tries to apply all input values to the entity, validates the entity and
	 * saves all attribute owners.
	 * <p>
	 * Reverts all changes to the entity if the validation fails.
	 * <p>
	 * This method reflects the validation result in the table.
	 *
	 * @return <i>true</i> if validation and saving succeeded, <i>false</i>
	 *         otherwise
	 */
	public boolean tryToApplyValidateAndSave() {

		try {
			new EmfAttributesApplier<>(tableRow, inputRows)//
				.addAdditionalValidators(additionalValidators)
				.applyValidateAndSave();
			clearDiagnostics();
			return true;
		} catch (EmfValidationException exception) {
			showDiagnostics(exception.getValidationResult());
			return false;
		}
	}

	// ------------------------------ validation ------------------------------ //

	public void addAdditionalValidator(IEmfValidator<R> validator) {

		additionalValidators.add(validator);
	}

	public void addAdditionalValidators(Collection<IEmfValidator<R>> validators) {

		additionalValidators.addAll(validators);
	}

	// ------------------------------ diagnostics ------------------------------ //

	private void clearDiagnostics() {

		clearValidationDiv();
		clearDiagnosticsOnInputs();
	}

	private void showDiagnostics(IEmfValidationResult validationResult) {

		var shownAttributes = showDiagnosticsOnInputs(validationResult);
		updateValidationDiv(validationResult, shownAttributes);
	}

	// ------------------------------ diagnostics on inputs ------------------------------ //

	private void clearDiagnosticsOnInputs() {

		getInputRows().forEach(row -> row.clear());
	}

	private Set<IEmfAttribute<?, ?>> showDiagnosticsOnInputs(IEmfValidationResult validationResult) {

		Set<IEmfAttribute<?, ?>> shownAttributes = new HashSet<>();
		for (EmfAttributeValueInputFrame<R, ?> inputRow: getInputRows()) {
			inputRow.showDiagnostics(validationResult);
			shownAttributes.add(inputRow.getAttribute().getOriginalAttribute());
		}
		return shownAttributes;
	}

	// ------------------------------ diagnostics display ------------------------------ //

	private void updateValidationDiv(IEmfValidationResult validationResult, Set<IEmfAttribute<?, ?>> shownAttributes) {

		clearValidationDiv();
		this.validationDiv = new EmfAttributesDivValidationDiv<>(validationResult, shownAttributes);
		appendChild(validationDiv);
	}

	private void clearValidationDiv() {

		if (validationDiv != null) {
			validationDiv.disappend();
			validationDiv = null;
		}
	}
}
