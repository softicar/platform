package com.softicar.platform.emf.form;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.editor.EmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormAttributesDiv<R extends IEmfTableRow<R, ?>> extends EmfAttributesDiv<R> {

	public EmfFormAttributesDiv(R tableRow, boolean editMode) {

		super(tableRow, editMode);

		for (IEmfAttribute<R, ?> attribute: tableRow.table().getAttributes()) {
			if (attribute.isConcealed()) {
				// skip concealed attributes
			} else if (tableRow.impermanent() && attribute.isTransient()) {
				// skip transient attributes in creation mode
			} else {
				addAttribute(attribute);
			}
		}
	}
}
