package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.edit.EmfEntityInputEditButton;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.auto.complete.EmfAutoCompleteBrowseButton;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;

public class EmfEntityInput<E extends IEmfEntity<E, ?>> extends DomAutoCompleteInput<E> implements IEmfInput<E> {

	private final EmfEntityInputEditButton<E> editButton;

	public EmfEntityInput(IDomAutoCompleteInputEngine<E> inputEngine) {

		super(inputEngine);
		this.editButton = new EmfEntityInputEditButton<>(this);
		inputBar.appendChild(new EmfAutoCompleteBrowseButton(this, inputEngine));
		inputBar.appendChild(editButton);
		addInputConstraintRefreshCallback(() -> editButton.refresh(getValueNoThrow().orElse(null)));
	}

	public <S> EmfEntityInput(IEmfEntityTable<E, ?, S> targetTable, S scope) {

		this(new EmfEntityInputEngine<>(targetTable, scope));
	}

	@Override
	public void setValue(E value) {

		super.setValue(value);
		editButton.refresh(value);
	}
}
