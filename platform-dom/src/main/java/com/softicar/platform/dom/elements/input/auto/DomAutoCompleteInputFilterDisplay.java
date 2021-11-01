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

	public DomAutoCompleteInputFilterDisplay() {

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INPUT_FILTER_DISPLAY);
	}

	public void refresh(IDomAutoCompleteInputEngine<?> engine) {

		removeChildren();

		for (IDomAutoCompleteInputFilter filter: engine.getFilters()) {
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
