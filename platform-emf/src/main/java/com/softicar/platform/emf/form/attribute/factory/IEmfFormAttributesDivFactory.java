package com.softicar.platform.emf.form.attribute.factory;

import com.softicar.platform.emf.form.IEmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormAttributesDivFactory<R extends IEmfTableRow<R, ?>> {

	IEmfAttributesDiv<R> createAttributesDiv(R tableRow, boolean editMode);
}
