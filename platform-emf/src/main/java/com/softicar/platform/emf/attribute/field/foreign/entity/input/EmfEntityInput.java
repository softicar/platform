package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;

public class EmfEntityInput<E extends IEmfEntity<E, ?>> extends DomAutoCompleteEntityInput<E> implements IEmfInput<E> {

	public EmfEntityInput() {

		super();
	}

	public EmfEntityInput(IDomAutoCompleteInputEngine<E> inputEngine) {

		super(inputEngine);
	}

	public EmfEntityInput(IEmfEntityTable<E, ?, ?> table) {

		this(new EmfEntityInputEngine<>(table));
	}

	public <S extends IEntity> EmfEntityInput(IEmfEntityTable<E, ?, S> targetTable, S scope) {

		this(new EmfEntityInputEngine<>(targetTable, scope));
	}

	@Override
	public void refreshInputConstraints() {

		refreshFilters();
	}

	@Override
	public E getValueOrThrow() throws EmfInputException {

		return getSelection().getValueOrThrow();
	}

	@Override
	public void setMandatory(boolean mandatory) {

		getConfiguration().setMandatory(mandatory);
	}
}
