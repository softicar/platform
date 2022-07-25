package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.auto.complete.EmfAutoCompleteBrowseButton;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;

public class EmfEntityInput<E extends IEmfEntity<E, ?>> extends DomAutoCompleteInput<E> implements IEmfInput<E> {

	public EmfEntityInput(IDomAutoCompleteInputEngine<E> inputEngine) {

		super(inputEngine);

		inputBar.appendChild(new EmfAutoCompleteBrowseButton(this, inputEngine));
	}

	public <S> EmfEntityInput(IEmfEntityTable<E, ?, S> targetTable, S scope) {

		this(new EmfEntityInputEngine<>(targetTable, scope));
	}

	@Override
	public void setMandatory(boolean mandatory) {

		getConfiguration().setMandatory(mandatory);
	}
}
