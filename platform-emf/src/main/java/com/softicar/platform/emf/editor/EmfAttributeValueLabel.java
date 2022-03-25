package com.softicar.platform.emf.editor;

import com.softicar.platform.dom.elements.DomLabel;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAttributeValueLabel<R extends IEmfTableRow<R, ?>, V> extends DomLabel {

	public EmfAttributeValueLabel(IEmfAttribute<R, V> attribute, R row) {

		var title = attribute.getTitle();

		if (attribute.isMandatory(row) && !EmfBooleanAttribute.class.isInstance(attribute)) {
			title = title.concat("*");
		}

		appendText(title);
	}
}
