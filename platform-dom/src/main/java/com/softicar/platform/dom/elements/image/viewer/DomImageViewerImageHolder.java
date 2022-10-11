package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTag;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

class DomImageViewerImageHolder extends DomDiv {

	private final DomImageViewer viewer;
	private final Collection<DomImageViewerTag> tags;

	public DomImageViewerImageHolder(DomImageViewer viewer, DomImageViewerCanvas canvas) {

		this.viewer = Objects.requireNonNull(viewer);
		this.tags = new ArrayList<>();

		addMarker(DomTestMarker.IMAGE_VIEWER_IMAGE_HOLDER);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE_HOLDER);

		getDomEngine()//
			.makeDraggable(this, this)
			.setDragScrollNode(this, canvas);
	}

	public void refreshImage() {

		removeChildren();
		tags.clear();

		var tagCanvas = new DomImageViewerImageTagCanvas();
		appendChild(tagCanvas);

		var image = new DomImageViewerImage(viewer.getPageImage());
		tagCanvas.appendChild(image);

		viewer//
			.getPageTagDefinitions()
			.stream()
			.map(DomImageViewerTag::new)
			.map(tagCanvas::appendChild)
			.forEach(tags::add);

		if (viewer.isWidthDefined()) {
			getDomEngine().setHeightAndWidthOnLoad(image, viewer);
		}
	}

	public void applyTransformations() {

		var rotation = viewer.getRotation();
		var zoomPercentage = viewer.getZoomLevel().getPercentage();

		String rotationTransformation = getRotationTransformation(rotation);

		// center image when zoomed out
		String centeringTransformation;
		if (zoomPercentage < 100) {
			setStyle(CssStyle.LEFT, new CssPercent(50));
			centeringTransformation = getCenteringTransformation(rotation);
		} else {
			unsetStyle(CssStyle.LEFT);
			centeringTransformation = "";
		}

		setStyle(CssStyle.TRANSFORM, "%s %s".formatted(rotationTransformation, centeringTransformation));
		setStyle(CssStyle.WIDTH, new CssPercent(zoomPercentage));

		tags.forEach(tag -> tag.applyRotation(rotation));
	}

	private String getRotationTransformation(DomImageViewerRotation rotation) {

		return switch (rotation) {
		case _0:
			yield "rotate(0deg) translate(0, 0)";
		case _90:
			yield "rotate(90deg) translate(0, -100%)";
		case _180:
			yield "rotate(180deg) translate(-100%, -100%)";
		case _270:
			yield "rotate(270deg) translate(-100%, 0)";
		};
	}

	private String getCenteringTransformation(DomImageViewerRotation rotation) {

		return switch (rotation) {
		case _0:
			yield "translate(-50%, 0)";
		case _90:
			yield "translate(0, 50%)";
		case _180:
			yield "translate(50%, 0)";
		case _270:
			yield "translate(0, -50%)";
		};
	}
}
