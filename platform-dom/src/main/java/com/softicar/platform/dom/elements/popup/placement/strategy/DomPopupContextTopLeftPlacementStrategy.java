package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;

/**
 * Places the {@link DomPopup} in the top-left corner of its
 * {@link IDomPopupContext}.
 *
 * @author Alexander Schmidt
 */
public class DomPopupContextTopLeftPlacementStrategy implements IDomPopupPlacementStrategy {

	@Override
	public DomPopupPlacement getPlacement(IDomEvent event) {

		return new DomPopupPlacement(1, 2, DomPopupOffsetUnit.PERCENT, DomPopupXAlign.LEFT, DomPopupYAlign.TOP);
	}
}
