package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPosition}, based upon the coordinates of the
 * given {@link IDomEvent}.
 *
 * @author Alexander Schmidt
 */
public class DomPopupEventCoordinatesPositionStrategy implements IDomPopupPositionStrategy {

	@Override
	public DomPopupPosition getPosition(IDomEvent event) {

		if (event != null) {
			return new DomPopupPosition(//
				(int) Math.round(event.getClientX()),
				(int) Math.round(event.getClientY()),
				DomPopupXAlign.LEFT,
				DomPopupYAlign.TOP);
		} else {
			return new DomPopupViewportCenterPositionStrategy(CssPercent._50, CssPercent._25).getPosition(event);
		}
	}
}
