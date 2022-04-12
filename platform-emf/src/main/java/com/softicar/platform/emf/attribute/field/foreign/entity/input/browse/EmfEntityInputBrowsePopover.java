package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Objects;

/**
 * Filter-Popover for {@link EmfEntityInput} that shows every available
 * {@link IEmfEntity} of a given {@link IDomAutoCompleteInputEngine}.
 * <p>
 * When a row in this popover is clicked, the {@link EmfEntityInput} is
 * automatically filled with the corresponding {@link IEmfEntity}.
 *
 * @author Daniel Klose
 */
class EmfEntityInputBrowsePopover<E extends IEmfEntity<E, ?>> extends DomPopover {

	private final EmfEntityInput<E> input;

	public EmfEntityInputBrowsePopover(EmfEntityInput<E> input, IDomAutoCompleteInputEngine<E> inputEngine) {

		this.input = Objects.requireNonNull(input);
		addCssClass(EmfCssClasses.EMF_ENTITY_INPUT_BROWSE_POPOVER);
		var table = new EmfEntityInputBrowseTable<>(Objects.requireNonNull(inputEngine));
		new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getNameColumn(), new EmfEntityInputBrowseColumnHandler<>(this::setInputValueAndHide))
			.setPageSize(DomAutoCompleteList.MAXIMUM_ELEMENT_TO_DISPLAY)
			.buildAndAppendTo(this);
	}

	private void setInputValueAndHide(E entity) {

		input.setValueAndHandleChangeCallback(entity);
		hide();
	}
}
