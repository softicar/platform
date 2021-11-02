package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.editor.EmfAttributeValueInputFrame;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfForeignEntityAttributeEditorDiv<R extends IEmfTableRow<R, ?>, V> extends DomDiv {

	private final R tableRow;
	private final Optional<EmfAttributeValueInputFrame<R, V>> inputRow;

	public EmfForeignEntityAttributeEditorDiv(IDbField<R, V> field, R tableRow) {

		this.tableRow = tableRow;
		this.inputRow = createInputRow(field, tableRow);
		if (inputRow.isPresent()) {
			appendChild(inputRow.get());
		} else {
			appendErrorMessage();
		}
	}

	public void save() {

		if (inputRow.isPresent()) {
			inputRow.get().applyToTableRow();
			tableRow.save();
		}
	}

	private Optional<EmfAttributeValueInputFrame<R, V>> createInputRow(IDbField<R, V> field, R tableRow) {

		if (field != null && tableRow != null) {
			IEmfTable<R, ?, ?> table = tableRow.table();
			IEmfAttribute<R, V> attribute = table.getAttribute(field);
			Optional<IEmfInput<V>> optionalEmfInput = attribute.createInput(tableRow);
			if (optionalEmfInput.isPresent()) {
				IEmfInput<V> emfInput = optionalEmfInput.get();
				EmfAttributeValueInputFrame<R, V> inputRow = new EmfAttributeValueInputFrame<>(attribute, tableRow, emfInput);
				inputRow.applyFromTableRow();
				return Optional.of(inputRow);
			}
		}
		return Optional.empty();
	}

	private void appendErrorMessage() {

		appendChild(new DomMessageDiv(DomMessageType.ERROR, EmfI18n.FAILED_TO_CREATE_THE_INPUT_ELEMENT));
	}
}
