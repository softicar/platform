package com.softicar.platform.emf.attribute.input.auto.complete;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;

public class EmfAutoCompleteBrowseButton extends DomPopupButton {

	public <T> EmfAutoCompleteBrowseButton(DomAutoCompleteInput<T> input, IDomAutoCompleteInputEngine<T> inputEngine) {

		setPopupFactory(() -> new EmfAutoCompleteBrowsePopover<>(input, inputEngine));
		setIcon(EmfImages.FIND.getResource());
		setTitle(EmfI18n.BROWSE.concatEllipsis());
		setTabIndex(-1);
	}
}
