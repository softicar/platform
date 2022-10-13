package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;

public class DomPopupBelowEventNodePositionStrategy implements IDomPopupPositionStrategy {

	@Override
	public DomPopupPosition getPosition(IDomEvent event) {

		if (event != null) {
			var rect = event.getBoundingClientRect();
			int x = Double.valueOf(rect.getX() + rect.getWidth()).intValue();
			int y = Double.valueOf(rect.getY() + rect.getHeight()).intValue();
			return new DomPopupPosition(x, y, DomPopupXAlign.RIGHT, DomPopupYAlign.TOP);
		} else {
			return new DomPopupViewportCenterPositionStrategy(CssPercent._50, CssPercent._25).getPosition(event);
		}
	}
}
