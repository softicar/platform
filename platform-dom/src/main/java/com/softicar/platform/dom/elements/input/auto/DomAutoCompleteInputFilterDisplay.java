package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.bar.DomBar;

class DomAutoCompleteInputFilterDisplay extends DomDiv {

	private final IDomAutoCompleteInputEngine<?> inputEngine;

	public DomAutoCompleteInputFilterDisplay(IDomAutoCompleteInputEngine<?> inputEngine) {

		this.inputEngine = inputEngine;

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INPUT_FILTER_DISPLAY);
		refresh();
	}

	public void refresh() {

		removeChildren();

		for (IDomAutoCompleteInputFilter filter: inputEngine.getFilters()) {
			if (filter.isActive()) {
				appendFilter(filter);
			}
		}
	}

	private void appendFilter(IDomAutoCompleteInputFilter filter) {

		if (filter.isActive()) {
			IDisplayString filterTitle = filter.getFilterTitle().concat(": ");
			IDisplayString filterValueTitle = filter.getValueTitle();

			DomBar bar = appendChild(new DomBar());
			bar.setTitle(createActiveFilterMessage(filterTitle, filterValueTitle));
			bar.appendChild(new DomImage(DomElementsImages.FILTER.getResource()));
			bar//
				.appendNewChild(DomElementTag.B)//
				.appendText(filterTitle);
			bar.appendText(filterValueTitle);
		}
	}

	private IDisplayString createActiveFilterMessage(IDisplayString filterTitle, IDisplayString filterValueTitle) {

		return DomI18n.ACTIVE_FILTER//
			.concat(": ")
			.concat(filterTitle)
			.concat(filterValueTitle);
	}
}
