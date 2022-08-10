package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.entity.IEmfEntity;

public interface IEmfEntityInput<E extends IEmfEntity<E, ?>> extends IEmfInput<E> {

	/**
	 * This method can be overwritten to define logic that should additionally
	 * be executed in {@link #refreshInputConstraints()}.
	 * <p>
	 * TODO Get better javadoc ideas
	 */
	default void refreshInput() {

		//nothing by default
	}
}
