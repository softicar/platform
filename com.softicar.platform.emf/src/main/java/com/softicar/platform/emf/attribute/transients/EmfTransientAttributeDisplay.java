package com.softicar.platform.emf.attribute.transients;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfTransientAttributeDisplay<V> extends DomDiv {

	public EmfTransientAttributeDisplay(V value) {

		if (value != null) {
			appendText(value.toString());
		}
	}
}
