package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;
import java.util.Optional;

abstract class AbstractDomPopupViewportAlignedPlacementStrategy implements IDomPopupPlacementStrategy {

	private final CssPercent xPercent;
	private final CssPercent yPercent;
	private final DomPopupXAlign xAlign;
	private final DomPopupYAlign yAlign;

	public AbstractDomPopupViewportAlignedPlacementStrategy(CssPercent xPercent, CssPercent yPercent, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		this.xPercent = xPercent;
		this.yPercent = yPercent;
		this.xAlign = xAlign;
		this.yAlign = yAlign;
	}

	@Override
	public DomPopupPlacement getPlacement(IDomEvent event) {

		int x = (int) Math.round(Optional.ofNullable(event).map(IDomEvent::getWindowWidth).orElse(0.0) / 100 * xPercent.getValue());
		int y = (int) Math.round(Optional.ofNullable(event).map(IDomEvent::getWindowHeight).orElse(0.0) / 100 * yPercent.getValue());
		return new DomPopupPlacement(x, y, DomPopupOffsetUnit.PIXELS, xAlign, yAlign);
	}
}
