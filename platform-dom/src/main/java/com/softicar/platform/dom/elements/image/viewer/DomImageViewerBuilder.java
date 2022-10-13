package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTagDefinition;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTagDefinitions;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;
import java.util.Objects;

/**
 * A builder for {@link DomImageViewer}.
 *
 * @author Alexander Schmidt
 */
public class DomImageViewerBuilder {

	private List<? extends IResource> images;
	private ICssLength width;
	private final DomImageViewerTagDefinitions tagDefinitions;

	/**
	 * Constructs a new {@link DomImageViewerBuilder}.
	 */
	public DomImageViewerBuilder() {

		this.images = List.of();
		this.width = null;
		this.tagDefinitions = new DomImageViewerTagDefinitions();
	}

	/**
	 * Specifies the images to display in the {@link DomImageViewer}.
	 *
	 * @param images
	 *            the images (never <i>null</i>)
	 * @return this
	 */
	public DomImageViewerBuilder setImages(List<? extends IResource> images) {

		this.images = Objects.requireNonNull(images);
		return this;
	}

	/**
	 * Specifies a fixed width for the {@link DomImageViewer}.
	 * <p>
	 * If <i>null</i> is given, the {@link DomImageViewer} uses adaptive width.
	 *
	 * @param width
	 *            a fixed width (may be <i>null</i>)
	 * @return this
	 */
	public DomImageViewerBuilder setWidth(ICssLength width) {

		this.width = width;
		return this;
	}

	/**
	 * Same as {@link #addTag(int, double, double, double, double, String)} but
	 * without a caption.
	 */
	public DomImageViewerBuilder addTag(int pageIndex, double x, double y, double width, double height) {

		return addTag(pageIndex, x, y, width, height, (String) null);
	}

	/**
	 * Same as {@link #addTag(int, double, double, double, double, String)} but
	 * with an {@link IDisplayString} caption.
	 */
	public DomImageViewerBuilder addTag(int pageIndex, double x, double y, double width, double height, IDisplayString caption) {

		return addTag(pageIndex, x, y, width, height, caption != null? caption.toString() : null);
	}

	/**
	 * Adds a tag, i.e. a rectangular marked region, to a page displayed in the
	 * {@link DomImageViewer}.
	 * <p>
	 * Placement and size of the rectangle are percent-based, relative to the
	 * dimensions of the displayed page.
	 * <p>
	 * The tag may have a caption.
	 *
	 * @param pageIndex
	 *            the zero-based index of the page on which the tag shall be
	 *            displayed
	 * @param x
	 *            the horizontal offset of the top-left corner, in percent
	 * @param y
	 *            the vertical offset of the top-left corner, in percent
	 * @param width
	 *            the horizontal size, in percent
	 * @param height
	 *            the vertical size, in percent
	 * @param caption
	 *            the caption (may be <i>null</i>)
	 */
	public DomImageViewerBuilder addTag(int pageIndex, double x, double y, double width, double height, String caption) {

		tagDefinitions.add(pageIndex, new DomImageViewerTagDefinition(x, y, width, height, caption));
		return this;
	}

	/**
	 * Constructs and returns a new {@link DomImageViewer}.
	 *
	 * @return a {@link DomImageViewer} (never <i>null</i>)
	 */
	public DomImageViewer build() {

		return new DomImageViewer(images, tagDefinitions, width);
	}
}
