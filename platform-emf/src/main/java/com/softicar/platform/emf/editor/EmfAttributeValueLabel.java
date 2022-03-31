package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomLabel;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAttributeValueLabel<R extends IEmfTableRow<R, ?>, V> extends DomLabel {

	private final IEmfAttribute<R, V> attribute;
	private EmfAttributeValueMode valueMode;

	public EmfAttributeValueLabel(IEmfAttribute<R, V> attribute, EmfAttributeValueMode valueMode) {

		this.attribute = attribute;
		this.valueMode = valueMode;
		refresh();
	}

	public void refresh(EmfAttributeValueMode valueMode) {

		if (valueMode != this.valueMode) {
			this.valueMode = valueMode;
			refresh();
		}
	}

	private void refresh() {

		setDisplayNone(valueMode.isHidden());

		removeChildren();
		appendText(getTitle());
	}

	private IDisplayString getTitle() {

		var title = attribute.getTitle();

		if (valueMode.isMandatory() && !EmfBooleanAttribute.class.isInstance(attribute)) {
			title = title.concat("*");
		}

		return title;
	}
}
