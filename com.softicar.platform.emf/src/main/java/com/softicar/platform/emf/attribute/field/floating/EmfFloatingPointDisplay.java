package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfFloatingPointDisplay<V> extends DomDiv {

	public EmfFloatingPointDisplay(IEmfFloatingPointAttributeStrategy<V> strategy, V value) {

		if (value != null) {
			appendText(strategy.formatValue(value));
		}
	}
}
