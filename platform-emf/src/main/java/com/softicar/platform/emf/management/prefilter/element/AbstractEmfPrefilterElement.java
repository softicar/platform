package com.softicar.platform.emf.management.prefilter.element;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.management.prefilter.AbstractEmfPrefilterRow;
import com.softicar.platform.emf.management.prefilter.IEmfPrefilterElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractEmfPrefilterElement<S, E extends IEmfTableRow<E, ?>, T extends IEmfTableRow<T, ?>, R extends AbstractEmfPrefilterRow<E>>
		extends DomDiv
		implements IEmfPrefilterElement<T> {

	private final IDisplayString caption;
	private final S scope;
	private final Function<S, R> rowFactory;
	private final List<R> filterRows;
	private final List<DomButton> filterTypeButtons;
	private final DomDiv filterDivContent;
	private FilterType filterType;

	public AbstractEmfPrefilterElement(IDisplayString caption, S scope, Function<S, R> rowFactory) {

		this.caption = caption;
		this.scope = scope;
		this.rowFactory = rowFactory;
		this.filterRows = new ArrayList<>();
		this.filterType = FilterType.AND;
		this.filterTypeButtons = new ArrayList<>();
		this.filterDivContent = appendChild(new DomDiv());
		addCssClass(EmfCssClasses.EMF_PREFILTER_ELEMENT);
		appendFirstRow();
		appendAddRowButton();
	}

	@Override
	public IDisplayString getCaption() {

		return caption;
	}

	@Override
	public void reset() {

		this.filterRows.clear();
		this.filterTypeButtons.clear();
		this.filterType = FilterType.AND;
		this.filterDivContent.removeChildren();
		appendFirstRow();
	}

	protected S getScope() {

		return scope;
	}

	protected FilterType getFilterType() {

		return filterType;
	}

	protected List<R> getFilterRows() {

		return filterRows//
			.stream()
			.filter(row -> !row.isEmpty())
			.filter(row -> row.getFilterExpression() != null)
			.collect(Collectors.toList());
	}

	private void appendFirstRow() {

		R row = rowFactory.apply(scope);
		row.appendContent();
		appendRemoveRowButton(row, true);
		registerRow(row);
	}

	private void appendAddRowButton() {

		DomActionBar addFilterBar = appendChild(
			new DomActionBar(
				new DomButton()//
					.setIcon(DomElementsImages.FILTER_ADD.getResource())//
					.setLabel(EmfI18n.ADD_FILTER)//
					.setClickCallback(() -> addRow(rowFactory.apply(scope)))));
		addFilterBar.addCssClass(EmfCssClasses.EMF_PREFILTER_ADD_FILTER_BAR);
	}

	private void appendRemoveRowButton(R targetRow, boolean disabled) {

		DomButton removeFilterButton = new DomButton()//
			.setIcon(DomElementsImages.FILTER_REMOVE.getResource())//
			.setDisabled(disabled)
			.setClickCallback(() -> removeRow(targetRow));
		targetRow.appendRemoveRowButton(removeFilterButton);
	}

	private void removeRow(R row) {

		if (filterRows.contains(row)) {
			filterRows.remove(row);
			filterDivContent.removeChild(row);
		}
	}

	private void addRow(R row) {

		appendFilterTypeButton(row);
		appendRemoveRowButton(row, false);
		registerRow(row);
	}

	private void registerRow(R row) {

		filterRows.add(row);
		filterDivContent.appendChild(row);
	}

	private void appendFilterTypeButton(R targetRow) {

		DomButton filterTypeButton = new DomButton()//
			.setIcon(EmfImages.ENTITY_PREFILTER_DOTS_T.getResource())//
			.setLabel(filterType.toDisplay())//
			.setClickCallback(() -> switchFilterType());
		filterTypeButton.addCssClass(EmfCssClasses.EMF_PREFILTER_FILTER_TYPE_BUTTON);
		filterTypeButtons.add(filterTypeButton);
		targetRow.appendContent(filterTypeButton);
	}

	private void switchFilterType() {

		if (filterType.equals(FilterType.AND)) {
			filterType = FilterType.OR;
		} else {
			filterType = FilterType.AND;
		}
		filterTypeButtons//
			.stream()
			.forEach(button -> button.setLabel(filterType.toDisplay()));
	}

	public enum FilterType {

		AND(),
		OR();

		public IDisplayString toDisplay() {

			return IDisplayString.create(toString());
		}
	}
}
