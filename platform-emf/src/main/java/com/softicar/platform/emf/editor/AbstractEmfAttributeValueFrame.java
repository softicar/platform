package com.softicar.platform.emf.editor;

import com.softicar.platform.dom.elements.input.diagnostics.DomInputDiagnosticsFrame;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfAttributeValueFrame<R extends IEmfTableRow<R, ?>, V> extends DomInputDiagnosticsFrame
		implements IEmfAttributeValueFrame<R, V> {

	protected final IEmfAttribute<R, V> attribute;

	public AbstractEmfAttributeValueFrame(IEmfAttribute<R, V> attribute, IDomNode valueNode) {

		super(valueNode);

		this.attribute = attribute;

		setMarker(attribute.getTestMarker());
	}

	@Override
	public IEmfAttribute<R, V> getAttribute() {

		return attribute;
	}
}
