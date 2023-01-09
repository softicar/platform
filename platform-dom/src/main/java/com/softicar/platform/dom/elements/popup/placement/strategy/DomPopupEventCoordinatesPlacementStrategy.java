package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPlacement}, based upon the coordinates of the
 * given {@link IDomEvent}.
 *
 * @author Alexander Schmidt
 */
public class DomPopupEventCoordinatesPlacementStrategy implements IDomPopupPlacementStrategy {

	@Override
	public DomPopupPlacement getPlacement(IDomEvent event) {

		if (event != null) {
			return new DomPopupPlacement(//
				(int) Math.round(event.getClientX()),
				(int) Math.round(event.getClientY()),
				DomPopupOffsetUnit.PIXELS,
				DomPopupXAlign.LEFT,
				DomPopupYAlign.TOP);
		} else {
			return new DomPopupViewportCenterPlacementStrategy(CssPercent._50, CssPercent._25).getPlacement(event);
		}
	}
}
