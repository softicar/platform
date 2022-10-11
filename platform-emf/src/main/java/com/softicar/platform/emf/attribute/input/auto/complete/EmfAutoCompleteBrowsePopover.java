package com.softicar.platform.emf.attribute.input.auto.complete;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.popup.popover.action.DomPopover;
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
class EmfAutoCompleteBrowsePopover<T> extends DomPopover {

	private final DomAutoCompleteInput<T> input;

	public EmfAutoCompleteBrowsePopover(DomAutoCompleteInput<T> input, IDomAutoCompleteInputEngine<T> inputEngine) {

		this.input = Objects.requireNonNull(input);
		addCssClass(EmfCssClasses.EMF_ENTITY_INPUT_BROWSE_POPOVER);
		var table = new EmfAutoCompleteBrowseTable<>(Objects.requireNonNull(inputEngine));
		new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getNameColumn(), new EmfAutoCompleteBrowseColumnHandler<>(inputEngine, this::setInputValueAndHide))
			.setPageSize(DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY)
			.setHideNavigationActionButtons(true)
			.buildAndAppendTo(this);
	}

	private void setInputValueAndHide(T entity) {

		input.setValueAndHandleChangeCallback(entity);
		close();
	}
}
