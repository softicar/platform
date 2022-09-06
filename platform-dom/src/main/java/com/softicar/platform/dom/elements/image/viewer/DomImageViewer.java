package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;

public class DomImageViewer extends DomDiv {

	private final List<? extends IResource> images;
	private final DomImageViewerToolBar toolBar;
	private final DomImageViewerCanvas canvas;
	private DomImageViewerZoomLevel zoomLevel;
	private boolean rotated;
	private int pageIndex;

	public DomImageViewer(List<? extends IResource> images) {

		this.images = images;
		this.toolBar = new DomImageViewerToolBar(this);
		this.canvas = new DomImageViewerCanvas(this);
		this.zoomLevel = DomImageViewerZoomLevel.getDefault();
		this.rotated = false;
		this.pageIndex = 0;

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER);

		appendChild(toolBar);
		appendChild(canvas);

		refresh();
	}

	// ------------------------------ size ------------------------------ //

	public DomImageViewer setWidth(ICssLength width) {

		setStyle(CssStyle.WIDTH, width);
		return this;
	}

	public DomImageViewer setHeight(ICssLength height) {

		setStyle(CssStyle.HEIGHT, height);
		return this;
	}

	// ------------------------------ zoom ------------------------------ //

	public DomImageViewerZoomLevel getZoomLevel() {

		return zoomLevel;
	}

	public void setZoomLevel(DomImageViewerZoomLevel zoomLevel) {

		this.zoomLevel = zoomLevel;

		toolBar.refresh();
		canvas.applyTransformations();
	}

	public void zoomIn() {

		setZoomLevel(zoomLevel.getNext());
	}

	public void zoomOut() {

		setZoomLevel(zoomLevel.getPrevious());
	}

	// ------------------------------ page ------------------------------ //

	public int getPageCount() {

		return images.size();
	}

	public IResource getPageImage() {

		return images.get(pageIndex);
	}

	public int getPageIndex() {

		return pageIndex;
	}

	public void showNextPage() {

		pageIndex++;
		refresh();
	}

	public void showPreviousPage() {

		pageIndex--;
		refresh();
	}

	// ------------------------------ rotate ------------------------------ //

	public boolean isRotated() {

		return rotated;
	}

	public void rotate() {

		this.rotated = !rotated;
		canvas.applyTransformations();
	}

	// ------------------------------ internal ------------------------------ //

	private void refresh() {

		toolBar.refresh();
		canvas.refresh();
	}
}
