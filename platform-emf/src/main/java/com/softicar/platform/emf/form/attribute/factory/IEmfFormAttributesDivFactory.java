package com.softicar.platform.emf.form.attribute.factory;

import com.softicar.platform.emf.form.IEmfFormAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormAttributesDivFactory<R extends IEmfTableRow<R, ?>> {

	IEmfFormAttributesDiv<R> createAttributesDiv(R tableRow, boolean editMode);
}
