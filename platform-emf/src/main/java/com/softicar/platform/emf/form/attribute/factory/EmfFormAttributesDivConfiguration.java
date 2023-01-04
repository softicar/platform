package com.softicar.platform.emf.form.attribute.factory;

import com.softicar.platform.emf.form.EmfFormAttributesDiv;
import com.softicar.platform.emf.form.IEmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormAttributesDivConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormAttributesDivConfiguration<R> {

	private IEmfFormAttributesDivFactory<R> attributesDivFactory;

	public EmfFormAttributesDivConfiguration() {

		this.attributesDivFactory = (tableRow, editMode) -> new EmfFormAttributesDiv<>(tableRow, editMode);
	}

	@Override
	public IEmfAttributesDiv<R> createAttributesDiv(R tableRow, boolean editMode) {

		return attributesDivFactory.createAttributesDiv(tableRow, editMode);
	}

	public EmfFormAttributesDivConfiguration<R> setAttributesDivFactory(IEmfFormAttributesDivFactory<R> attributesDivFactory) {

		this.attributesDivFactory = attributesDivFactory;
		return this;
	}
}
