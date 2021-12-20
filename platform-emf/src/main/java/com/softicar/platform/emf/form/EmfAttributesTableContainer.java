package com.softicar.platform.emf.form;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;

class EmfAttributesTableContainer<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final EmfFormAttributesDiv<R> attributesDiv;

	public EmfAttributesTableContainer(R tableRow, boolean editMode) {

		this.attributesDiv = new EmfFormAttributesDiv<>(tableRow, editMode);

		appendChild(attributesDiv);

		setCssClass(EmfCssClasses.EMF_ATTRIBUTES_TABLE_CONTAINER);
	}

	public EmfAttributesTableContainer<R> addAdditionalValidators(Collection<IEmfValidator<R>> validators) {

		attributesDiv.addAdditionalValidators(validators);
		return this;
	}

	public EmfFormAttributesDiv<R> getAttributesDiv() {

		return attributesDiv;
	}

	public void refresh() {

		attributesDiv.refresh();
	}
}
