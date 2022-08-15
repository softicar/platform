package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.edit.EmfEntityInputEditButton;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.auto.complete.EmfAutoCompleteBrowseButton;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;

public class EmfEntityInput<E extends IEmfEntity<E, ?>> extends DomAutoCompleteInput<E> implements IEmfInput<E> {

	private final EmfEntityInputEditButton<E> editButton;
	private INullaryVoidFunction refreshCallback;

	public EmfEntityInput(IDomAutoCompleteInputEngine<E> inputEngine) {

		super(inputEngine);
		inputBar.appendChild(new EmfAutoCompleteBrowseButton(this, inputEngine));
		inputBar.appendChild(this.editButton = new EmfEntityInputEditButton<>(this));
		this.refreshCallback = INullaryVoidFunction.NO_OPERATION;
	}

	public <S> EmfEntityInput(IEmfEntityTable<E, ?, S> targetTable, S scope) {

		this(new EmfEntityInputEngine<>(targetTable, scope));
	}

	public void setRefreshCallback(INullaryVoidFunction refreshCallback) {

		this.refreshCallback = refreshCallback;
	}

	@Override
	public void setValue(E value) {

		super.setValue(value);
		editButton.refresh(getValueNoThrow());
	}

	@Override
	public void setMandatory(boolean mandatory) {

		getConfiguration().setMandatory(mandatory);
	}

	@Override
	public final void refreshInputConstraints() {

		super.refreshInputConstraints();
		editButton.refresh(getValueNoThrow());
		refreshCallback.apply();
	}
}
