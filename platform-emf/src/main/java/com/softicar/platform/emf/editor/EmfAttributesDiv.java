package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbLazyTransaction;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A table to view and edit the attributes on an {@link IEmfTableRow}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfAttributesDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	protected final R tableRow;
	protected final boolean editMode;
	private final Collection<IEmfAttribute<R, ?>> attributes;
	private final Collection<EmfAttributeValueFrame<R, ?>> valueFrames;
	private final Map<IEmfAttribute<R, ?>, EmfAttributeValueLabel<R, ?>> attributeToValueLabelMap;
	private final Map<IEmfAttribute<R, ?>, EmfAttributeValueFrame<R, ?>> attributeToValueFrameMap;
	private final DomLabelGrid attributeGrid;
	private final List<IEmfValidator<R>> additionalValidators;
	private EmfAttributesDivValidationDiv<R> validationDiv;

	public EmfAttributesDiv(R tableRow, boolean editMode) {

		this.tableRow = tableRow;
		this.editMode = editMode;
		this.attributes = new ArrayList<>();
		this.valueFrames = new ArrayList<>();
		this.attributeToValueLabelMap = new HashMap<>();
		this.attributeToValueFrameMap = new HashMap<>();
		this.attributeGrid = new DomLabelGrid();
		this.additionalValidators = new ArrayList<>();
		this.validationDiv = null;

		appendChild(attributeGrid);
	}

	public void refresh() {

		attributes.forEach(attribute -> {
			var mode = getValueMode(attribute);
			attributeToValueLabelMap.get(attribute).refresh(mode);
			attributeToValueFrameMap.get(attribute).refresh(mode);
		});
	}

	// ------------------------------ attributes ------------------------------ //

	public void addAttribute(IDbField<R, ?> field) {

		addAttribute(tableRow.table().getAttribute(field));
	}

	public void addAttribute(IEmfAttribute<R, ?> attribute) {

		var valueMode = getValueMode(attribute);
		var valueLabel = new EmfAttributeValueLabel<>(attribute, valueMode);
		var valueFrame = new EmfAttributeValueFrame<>(this, attribute, tableRow, valueMode);

		attributes.add(attribute);
		attributeToValueLabelMap.put(attribute, valueLabel);
		attributeToValueFrameMap.put(attribute, valueFrame);
		attributeGrid.add(valueLabel, valueFrame);
		valueFrames.add(valueFrame);
	}

	private EmfAttributeValueMode getValueMode(IEmfAttribute<R, ?> attribute) {

		if (attribute.isVisible(tableRow)) {
			if (editMode && attribute.isEditable(tableRow)) {
				if (attribute.isMandatory(tableRow)) {
					return EmfAttributeValueMode.MANDATORY_INPUT;
				} else {
					return EmfAttributeValueMode.OPTIONAL_INPUT;
				}
			} else {
				return EmfAttributeValueMode.DISPLAY;
			}
		} else {
			return EmfAttributeValueMode.HIDDEN;
		}
	}

	void onInputValueChange() {

		// using transaction to apply the attribute values temporarily
		try (var transaction = new DbLazyTransaction()) {
			for (var valueFrame: valueFrames) {
				try {
					valueFrame.applyToTableRow();
				} catch (Exception exception) {
					// any failure to read the input value shall be ignored
					DevNull.swallow(exception);
				}
			}

			refresh();
		}
	}

	// ------------------------------ apply and save ------------------------------ //

	public void applyToTableRow() {

		valueFrames.forEach(row -> row.applyToTableRow());
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
			new EmfAttributesApplier<>(tableRow, valueFrames)//
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

		valueFrames.forEach(row -> row.clear());
	}

	private Set<IEmfAttribute<?, ?>> showDiagnosticsOnInputs(IEmfValidationResult validationResult) {

		var shownAttributes = new HashSet<IEmfAttribute<?, ?>>();
		for (var valueFrame: valueFrames) {
			valueFrame.showDiagnostics(validationResult);
			shownAttributes.add(valueFrame.getAttribute().getOriginalAttribute());
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
