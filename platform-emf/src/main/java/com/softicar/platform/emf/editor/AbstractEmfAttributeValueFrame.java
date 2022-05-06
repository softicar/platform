package com.softicar.platform.emf.editor;

import com.softicar.platform.dom.elements.input.diagnostics.DomInputDiagnosticsFrame;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfAttributeValueFrame<R extends IEmfTableRow<R, ?>, V> extends DomInputDiagnosticsFrame {

	protected final IEmfAttribute<R, V> attribute;

	public AbstractEmfAttributeValueFrame(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;

		addMarker(attribute.getTestMarker());
	}

	public IEmfAttribute<R, V> getAttribute() {

		return attribute;
	}
}
