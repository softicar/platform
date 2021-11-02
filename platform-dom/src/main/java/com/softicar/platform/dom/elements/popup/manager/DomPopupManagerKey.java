package com.softicar.platform.dom.elements.popup.manager;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.Objects;

/**
 * A key identifying an entity and the popup class.
 *
 * @author Oliver Richers
 */
class DomPopupManagerKey {

	private final Object entity;
	private final Class<? extends DomPopup> popupClass;

	public DomPopupManagerKey(Object entity, Class<? extends DomPopup> popupClass) {

		this.entity = entity;
		this.popupClass = popupClass;
	}

	@Override
	public int hashCode() {

		return Objects.hash(popupClass, entity);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DomPopupManagerKey) {
			DomPopupManagerKey other = (DomPopupManagerKey) object;
			return Objects.equals(popupClass, other.popupClass) && Objects.equals(entity, other.entity);
		} else {
			return false;
		}
	}
}
