package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;

public class EmfReadOnlyBasicEntityInput<V extends IEntity> extends AbstractEmfInputDiv<V> {

	private V value;

	@Override
	public V getValueOrThrow() {

		return value;
	}

	@Override
	public void setValue(V value) {

		this.value = value;

		removeChildren();
		if (value != null) {
			appendChild(value.toDisplay());
		}
	}
}
