package com.softicar.platform.dom.elements.image.viewer.tag;

import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewerRotation;
import com.softicar.platform.dom.style.CssStyle;
import java.util.Objects;
import java.util.Optional;

public class DomImageViewerTag extends DomDiv {

	private final DomImageViewerTagDefinition definition;
	private final Optional<DomImageViewerTagCaption> caption;

	public DomImageViewerTag(DomImageViewerTagDefinition definition) {

		this.definition = Objects.requireNonNull(definition);

		addMarker(DomTestMarker.IMAGE_VIEWER_TAG);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TAG);
		setStyle(CssStyle.LEFT, clampLeft() + "%");
		setStyle(CssStyle.TOP, clampTop() + "%");
		setStyle(CssStyle.WIDTH, clampWidth() + "%");
		setStyle(CssStyle.HEIGHT, clampHeight() + "%");

		this.caption = definition//
			.getCaption()
			.filter(it -> !it.isBlank())
			.map(DomImageViewerTagCaption::new)
			.map(this::appendChild);
	}

	public void applyRotation(DomImageViewerRotation rotation) {

		caption.ifPresent(it -> it.applyRotation(rotation));
	}

	private double clampLeft() {

		return Clamping.clamp(0, 100, definition.getX());
	}

	private double clampTop() {

		return Clamping.clamp(0, 100, definition.getY());
	}

	private double clampWidth() {

		var desiredWidth = definition.getWidth();
		var availableWidth = 100 - definition.getX();
		return Clamping.clamp(0, 100, Math.min(desiredWidth, availableWidth));
	}

	private double clampHeight() {

		var desiredHeight = definition.getHeight();
		var availableHeight = 100 - definition.getY();
		return Clamping.clamp(0, 100, Math.min(desiredHeight, availableHeight));
	}
}
