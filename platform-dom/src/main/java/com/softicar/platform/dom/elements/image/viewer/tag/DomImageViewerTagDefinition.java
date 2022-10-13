package com.softicar.platform.dom.elements.image.viewer.tag;

import java.util.Optional;

public class DomImageViewerTagDefinition {

	private final double x;
	private final double y;
	private final double width;
	private final double height;
	private final String caption;

	public DomImageViewerTagDefinition(double x, double y, double width, double height, String caption) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.caption = caption;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public double getWidth() {

		return width;
	}

	public double getHeight() {

		return height;
	}

	public Optional<String> getCaption() {

		return Optional.ofNullable(caption);
	}
}
