package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.event.IDomEvent;

/**
 * A strategy that devises a {@link DomPopupPlacement}.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IDomPopupPlacementStrategy {

	/**
	 * Derives a {@link DomPopupPlacement} from the given {@link IDomEvent}.
	 * <p>
	 * Implementations may completely ignore the given {@link IDomEvent}.
	 *
	 * @param event
	 *            the {@link IDomEvent} (may be <i>null</i>)
	 * @return the derived {@link DomPopupPlacement} (never <i>null</i>)
	 */
	DomPopupPlacement getPlacement(IDomEvent event);
}
