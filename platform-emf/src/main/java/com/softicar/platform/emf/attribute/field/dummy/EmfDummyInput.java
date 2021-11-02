package com.softicar.platform.emf.attribute.field.dummy;

import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;

public class EmfDummyInput<V> extends AbstractEmfInputDiv<V> {

	@Override
	public V getValueOrThrow() {

		return null;
	}

	@Override
	public void setValue(V value) {

		// nothing to do
	}
}
