package com.softicar.platform.emf.form;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.editor.EmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmfFormAttributesDiv<R extends IEmfTableRow<R, ?>> extends EmfAttributesDiv<R> {

	private final R tableRow;
	private final boolean editMode;

	public EmfFormAttributesDiv(R tableRow, boolean editMode) {

		super(tableRow);

		this.tableRow = tableRow;
		this.editMode = editMode;

		refresh();
	}

	public void refresh() {

		removeContent();

		for (IEmfAttribute<R, ?> attribute: getVisibleAttributes(tableRow)) {
			if (tableRow.impermanent() && attribute.isTransient()) {
				// skip transient attributes in creation mode
			} else {
				if (editMode && attribute.isEditable(tableRow)) {
					appendInputRow(attribute);
				} else {
					appendDisplayRow(attribute);
				}
			}
		}

		if (editMode) {
			applyFromEntity();
		}
	}

	private Collection<IEmfAttribute<R, ?>> getVisibleAttributes(R tableRow) {

		return tableRow//
			.table()
			.getAttributes()
			.stream()
			.filter(attribute -> attribute.isVisible(tableRow))
			.collect(Collectors.toList());
	}
}
