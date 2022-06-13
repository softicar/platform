package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.popup.modal.DomDismissablePopup;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.list.EmfDataTableFilterListDiv;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;

public class EmfDataTableFilterPopup<R> extends DomDismissablePopup implements IDomEnterKeyEventHandler {

	private final IEmfDataTableColumn<R, ?> column;
	private final EmfDataTableFilterListDiv<R> filterListDiv;
	private final DomCheckbox removeOtherFiltersCheckbox;
	private IEmfDataTableFilter<R> filter = new EmfDataTableNopFilter<>();

	public EmfDataTableFilterPopup(IEmfDataTableColumn<R, ?> column) {

		this.column = column;
		this.filterListDiv = new EmfDataTableFilterListDiv<>(column);
		this.removeOtherFiltersCheckbox = new DomCheckbox(false)//
			.setLabel(EmfDataTableI18n.REMOVE_FILTERS_FROM_OTHER_COLUMNS);

		setCaption(EmfDataTableI18n.FILTER_BY_COLUMN);
		setSubCaption(column.getTitle());
		addMarker(EmfDataTableDivMarker.FILTER_POPUP);
		column.getSettings().getMarkers().forEach(this::addMarker);

		appendChild(filterListDiv);
		appendChild(new DomActionBar(removeOtherFiltersCheckbox));
		appendActionNode(new ApplyFilterButton());
		appendCancelButton();
	}

	private class ApplyFilterButton extends DomButton {

		public ApplyFilterButton() {

			setIcon(DomElementsImages.FILTER.getResource());
			setLabel(EmfDataTableI18n.FILTER);
			addMarker(EmfDataTableDivMarker.FILTER_EXECUTE_BUTTON);
			setClickCallback(() -> submitFilter());
		}
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		submitFilter();
	}

	private void submitFilter() {

		// remove other filters if desired
		if (removeOtherFiltersCheckbox.isChecked()) {
			column.getController().removeAllColumnFiltersWithoutRefresh();
			column//
				.getController()
				.getColumnsInDefaultOrder()
				.stream()
				.filter(column -> column != this.column)
				.forEach(column -> column.resetFilterPopup());
		}

		// create new filter
		filter = filterListDiv.createFilter();
		if (filter instanceof EmfDataTableNopFilter) {
			column.removeColumnFilter();
		} else {
			column.setColumnFilter(filter);
		}

		close();
	}

	@Override
	public void open() {

		filter.reset();
		filterListDiv.updateAndRebuildFilterElements();
		super.open();
	}
}
