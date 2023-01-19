package com.softicar.platform.emf.form;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.form.attribute.factory.IEmfFormAttributesDivFactory;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;

class EmfAttributesTableContainer<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final IEmfFormAttributesDiv<R> attributesDiv;

	public EmfAttributesTableContainer(IEmfFormAttributesDivFactory<R> formAttributesDivFactory, R tableRow, boolean editMode) {

		this.attributesDiv = formAttributesDivFactory.createAttributesDiv(tableRow, editMode);

		appendChild(attributesDiv);
	}

	public EmfAttributesTableContainer<R> addAdditionalValidators(Collection<IEmfValidator<R>> validators) {

		attributesDiv.addAdditionalValidators(validators);
		return this;
	}

	public IEmfFormAttributesDiv<R> getAttributesDiv() {

		return attributesDiv;
	}

	public void refresh() {

		attributesDiv.refresh();
	}
}
