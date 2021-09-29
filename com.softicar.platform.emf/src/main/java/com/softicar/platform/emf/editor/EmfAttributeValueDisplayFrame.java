package com.softicar.platform.emf.editor;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAttributeValueDisplayFrame<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttributeValueFrame<R, V> {

	public EmfAttributeValueDisplayFrame(IEmfAttribute<R, V> attribute, R tableRow) {

		super(attribute, attribute.createDisplay(tableRow).orElseGet(DomDiv::new));
	}

	@Override
	public boolean isEditable() {

		return false;
	}

	@Override
	public boolean isMandatory() {

		return false;
	}
}
