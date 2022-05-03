package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

class DomPopupStateTracker {

	private final Set<DomPopup> openPopups;

	public DomPopupStateTracker() {

		this.openPopups = Collections.newSetFromMap(new WeakHashMap<>());
	}

	public void setOpen(DomPopup popup) {

		Objects.requireNonNull(popup);
		this.openPopups.add(popup);
	}

	public void setClosed(DomPopup popup) {

		Objects.requireNonNull(popup);
		this.openPopups.remove(popup);
	}

	public Collection<DomPopup> getAllOpen() {

		return Collections.unmodifiableCollection(openPopups);
	}

	public boolean isOpen(DomPopup popup) {

		Objects.requireNonNull(popup);
		return openPopups.contains(popup);
	}
}
