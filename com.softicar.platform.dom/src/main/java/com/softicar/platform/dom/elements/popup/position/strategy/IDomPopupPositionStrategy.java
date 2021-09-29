package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.event.IDomEvent;

/**
 * A strategy that devises a {@link DomPopupPosition}.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IDomPopupPositionStrategy {

	/**
	 * Derives a {@link DomPopupPosition} from the given {@link IDomEvent}.
	 * <p>
	 * Implementations may completely ignore the given {@link IDomEvent}.
	 *
	 * @param event
	 *            the {@link IDomEvent} (may be <i>null</i>)
	 * @return the derived {@link DomPopupPosition} (never <i>null</i>)
	 */
	DomPopupPosition getPosition(IDomEvent event);
}
