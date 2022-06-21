package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class DomPopupStateTracker {

	private final List<DomPopup> openPopups;

	public DomPopupStateTracker() {

		this.openPopups = new HashList<>();
	}

	public void setOpen(DomPopup popup) {

		Objects.requireNonNull(popup);
		this.openPopups.add(popup);
	}

	public void setClosed(DomPopup popup) {

		Objects.requireNonNull(popup);
		this.openPopups.remove(popup);
	}

	public List<DomPopup> getAllOpenInReverseOrder() {

		var popups = new ArrayList<>(openPopups);
		Collections.reverse(popups);
		return popups;
	}

	public boolean isOpen(DomPopup popup) {

		Objects.requireNonNull(popup);
		return openPopups.contains(popup);
	}

	public void clear() {

		openPopups.clear();
	}
}
