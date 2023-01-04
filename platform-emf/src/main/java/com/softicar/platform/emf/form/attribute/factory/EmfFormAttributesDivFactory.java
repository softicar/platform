package com.softicar.platform.emf.form.attribute.factory;

import com.softicar.platform.emf.form.IEmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormAttributesDivFactory<R extends IEmfTableRow<R, ?>> implements IEmfFormAttributesDivFactory<R> {

	private final IEmfFormAttributesDivFactory<R> attributesDivFactory;

	public EmfFormAttributesDivFactory(IEmfFormAttributesDivFactory<R> attributesDivFactory) {

		this.attributesDivFactory = attributesDivFactory;
	}

	@Override
	public IEmfAttributesDiv<R> createAttributesDiv(R tableRow, boolean editMode) {

		return attributesDivFactory.createAttributesDiv(tableRow, editMode);
	}
}
