package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.entity.IEmfEntity;

/**
 * Filter-Popover for {@link EmfEntityInput} that shows every available
 * {@link IEmfEntity} in a table.
 * <p>
 * When an entry in this popover is clicked, the {@link EmfEntityInput} is
 * automatically filled with the clicked {@link IEmfEntity}.
 * <p>
 * FIXME Currently it is not possible to either get the text from the
 * {@link EmfEntityInput} to filter for, nor can you manually set a filter on
 * the {@link AbstractInMemoryDataTable}.
 *
 * @author Daniel Klose
 */
class EmfEntityInputBrowsePopover<E extends IEmfEntity<E, ?>> extends DomPopover {

	private final EmfEntityInput<E> input;

	public EmfEntityInputBrowsePopover(EmfEntityInput<E> input, IDomAutoCompleteInputEngine<E> inputEngine) {

		this.input = input;
		addCssClass(EmfCssClasses.EMF_ENTITY_INPUT_BROWSE_POPOVER);
		EmfEntityInputBrowseTable<E> table = new EmfEntityInputBrowseTable<>(inputEngine);
		new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getNameColumn(), new EmfEntityInputBrowseColumnHandler<>(this::setInputValueAndHide))
			.setPageSize(DomAutoCompleteList.MAXIMUM_ELEMENT_TO_DISPLAY)
			.buildAndAppendTo(this);
	}

	private void setInputValueAndHide(E entity) {

		input.setValue(entity);
		hide();
	}
}
