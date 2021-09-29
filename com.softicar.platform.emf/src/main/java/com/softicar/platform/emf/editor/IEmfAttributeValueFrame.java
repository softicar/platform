package com.softicar.platform.emf.editor;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfAttributeValueFrame<R extends IEmfTableRow<R, ?>, V> {

	IEmfAttribute<R, V> getAttribute();

	boolean isEditable();

	boolean isMandatory();
}
