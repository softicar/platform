package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.elements.select.value.DomValueSelect;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

class DomImageViewerZoomSelect extends DomValueSelect<DomImageViewerZoomLevel> implements IDomChangeEventHandler {

	private final DomImageViewer viewer;

	public DomImageViewerZoomSelect(DomImageViewer viewer) {

		this.viewer = viewer;

		addValues(DomImageViewerZoomLevel.getAll());
		setSelectedValue(DomImageViewerZoomLevel.getDefault());
	}

	@Override
	public void handleChange(IDomEvent event) {

		viewer.setZoomLevel(getSelectedValue());
	}

	public void refresh() {

		setSelectedValue(viewer.getZoomLevel());
	}
}