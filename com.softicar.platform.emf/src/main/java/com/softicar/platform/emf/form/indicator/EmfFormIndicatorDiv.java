package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfCssClasses;

public class EmfFormIndicatorDiv extends DomDiv implements IDomClickEventHandler {

	private final IEmfFormIndicator indicator;

	public EmfFormIndicatorDiv(IEmfFormIndicator indicator, EmfFormIndicatorAlignment alignment) {

		this.indicator = indicator;
		setCssClass(EmfCssClasses.EMF_FORM_INDICATOR);
		setTitle(indicator.getTitle().toString());

		if (indicator.getClickAction().isPresent()) {
			addCssClass(DomCssPseudoClasses.CLICKABLE);
		} else {
			unlistenToEvent(DomEventType.CLICK);
		}

		switch (alignment) {
		case CENTER:
		case LEFT:
			appendChild(new IndicatorImage());
			appendText(indicator.getDisplayString());
			break;
		case RIGHT:
			appendText(indicator.getDisplayString());
			appendChild(new IndicatorImage());
			break;
		}
	}

	@Override
	public void handleClick(IDomEvent event) {

		if (indicator.getClickAction().isPresent()) {
			indicator.getClickAction().get().apply();
		}
	}

	private class IndicatorImage extends DomImage {

		public IndicatorImage() {

			super(indicator.getIcon());
			setCssClass(EmfCssClasses.EMF_FORM_INDICATOR_IMAGE);
		}
	}
}
