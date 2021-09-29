package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;
import java.util.Optional;

abstract class AbstractDomPopupViewportAlignmentPositionStrategy implements IDomPopupPositionStrategy {

	private final CssPercent xPercent;
	private final CssPercent yPercent;
	private final DomPopupXAlign xAlign;
	private final DomPopupYAlign yAlign;

	public AbstractDomPopupViewportAlignmentPositionStrategy(CssPercent xPercent, CssPercent yPercent, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		this.xPercent = xPercent;
		this.yPercent = yPercent;
		this.xAlign = xAlign;
		this.yAlign = yAlign;
	}

	@Override
	public DomPopupPosition getPosition(IDomEvent event) {

		int x = Optional.ofNullable(event).map(IDomEvent::getWindowWidth).orElse(0) / 100 * (int) Math.round(xPercent.getValue());
		int y = Optional.ofNullable(event).map(IDomEvent::getWindowHeight).orElse(0) / 100 * (int) Math.round(yPercent.getValue());
		return new DomPopupPosition(x, y, xAlign, yAlign);
	}
}
