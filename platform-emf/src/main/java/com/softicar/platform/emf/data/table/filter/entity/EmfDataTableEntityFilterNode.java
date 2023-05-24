package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import java.util.Optional;

public class EmfDataTableEntityFilterNode<R, T extends IEntity> extends AbstractEmfDataTableMultiTypeFilterDiv<R, EmfDataTableEntityFilterType> {

	private final IEmfDataTableColumn<R, T> column;
	private final EmfDataTableEntityFilterTypeSelect filterTypeSelect;
	private final DomAutoCompleteInput<T> entityInput;
	private final DomTextInput textInput;
	private final DomSpan dummyInput;

	public EmfDataTableEntityFilterNode(IEmfDataTableColumn<R, T> column) {

		this.column = column;
		this.filterTypeSelect = new EmfDataTableEntityFilterTypeSelect(this);
		this.entityInput = new EmfDataTableEntityFilterEntityInput<>(column);
		this.entityInput.addMarker(EmfTestMarker.DATA_TABLE_FILTER_INPUT_ENTITY);
		this.textInput = new DomTextInput();
		this.dummyInput = new DomSpan();
		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		return new EmfDataTableEntityFilterFactory<>(this).createFilter();
	}

	@Override
	public void refresh() {

		super.refresh();
	}

	@Override
	public IEmfDataTableFilterTypeSelect<EmfDataTableEntityFilterType> getFilterSelect() {

		return filterTypeSelect;
	}

	@Override
	public IDomNode getFilterInput(EmfDataTableEntityFilterType filterType) {

		return switch (filterType) {
		case CONTAINS_TEXT -> textInput;
		case DOES_NOT_CONTAIN_TEXT -> textInput;
		case IS -> entityInput;
		case IS_EMPTY -> dummyInput;
		case IS_NOT -> entityInput;
		case IS_NOT_EMPTY -> dummyInput;
		};
	}

	IEmfDataTableColumn<R, T> getColumn() {

		return column;
	}

	EmfDataTableEntityFilterState<T> getFilterState() {

		return new EmfDataTableEntityFilterState<>(//
			filterTypeSelect.getSelectedValue(),
			entityInput.getValueOrNull(),
			textInput.getValueText());
	}

	void applyFilterState(EmfDataTableEntityFilterState<T> filterState) {

		filterTypeSelect.setSelectedValue(filterState.getFilterType());
		entityInput.setValue(filterState.getFilterEntity().orElse(null));
		textInput.setValue(filterState.getFilterText());
	}

	static IDisplayString toDisplayStringSafely(IEntity entity) {

		return Optional//
			.ofNullable(entity)
			.map(IEntity::toDisplay)
			.orElse(IDisplayString.EMPTY);
	}
}
