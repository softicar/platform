package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Places the {@link DomPopup} with its top-right corner aligned to the
 * bottom-right corner of the event node.
 *
 * @author Alexander Schmidt
 */
public class DomPopupBelowEventNodePlacementStrategy implements IDomPopupPlacementStrategy {

	@Override
	public DomPopupPlacement getPlacement(IDomEvent event) {

		if (event != null) {
			var rect = event.getBoundingClientRect();
			int x = Double.valueOf(rect.getX() + rect.getWidth()).intValue();
			int y = Double.valueOf(rect.getY() + rect.getHeight()).intValue();
			return new DomPopupPlacement(x, y, DomPopupOffsetUnit.PIXELS, DomPopupXAlign.RIGHT, DomPopupYAlign.TOP);
		} else {
			return new DomPopupViewportCenterPlacementStrategy(CssPercent._50, CssPercent._25).getPlacement(event);
		}
	}
}
