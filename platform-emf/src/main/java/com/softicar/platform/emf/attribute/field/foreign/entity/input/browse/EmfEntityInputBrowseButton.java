package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.entity.IEmfEntity;

public class EmfEntityInputBrowseButton extends DomPopupButton {

	public <E extends IEmfEntity<E, ?>> EmfEntityInputBrowseButton(EmfEntityInput<E> input, IDomAutoCompleteInputEngine<E> inputEngine) {

		setPopupFactory(() -> new EmfEntityInputBrowsePopover<>(input, inputEngine));
		setIcon(EmfImages.FIND.getResource());
		setTitle(EmfI18n.BROWSE.concatEllipsis());
	}
}
