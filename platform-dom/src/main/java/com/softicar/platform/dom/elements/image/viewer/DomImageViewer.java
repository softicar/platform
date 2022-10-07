package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTagDefinition;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTagDefinitionMap;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Displays one or several image resources, and provides the following features:
 * <ul>
 * <li>paging</li>
 * <li>zooming</li>
 * <li>dragging</li>
 * <li>rotating</li>
 * </ul>
 * <p>
 * By default, the width of this element is adaptive. However, if a
 * "{@code width}" parameter is given, it is displayed with a fixed width.
 * <p>
 * For each page, <b>tags</b> (i.e. rectangular marked regions) can be defined
 * either via a {@link DomImageViewerTagDefinitionMap}, or via
 * {@link DomImageViewerBuilder}.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class DomImageViewer extends DomDiv {

	private final List<? extends IResource> images;
	private final DomImageViewerTagDefinitionMap tagDefinitionMap;
	private final DomImageViewerToolBar toolBar;
	private final DomImageViewerCanvas canvas;
	private final boolean widthDefined;
	private DomImageViewerZoomLevel zoomLevel;
	private DomImageViewerRotation rotation;
	private int pageIndex;
	private boolean tagsDisplayed;

	/**
	 * Constructs a new {@link DomImageViewer} for the given images.
	 *
	 * @param images
	 *            the images to display (never <i>null</i>)
	 */
	public DomImageViewer(List<? extends IResource> images) {

		this(images, null);
	}

	/**
	 * Constructs a new {@link DomImageViewer} for the given images.
	 *
	 * @param images
	 *            the images to display (never <i>null</i>)
	 * @param width
	 *            the width of this {@link DomImageViewer} (or <i>null</i> if
	 *            the width shall be adaptive)
	 */
	public DomImageViewer(List<? extends IResource> images, ICssLength width) {

		this(images, new DomImageViewerTagDefinitionMap(), width);
	}

	/**
	 * Constructs a new {@link DomImageViewer} for the given images.
	 *
	 * @param images
	 *            the images to display (never <i>null</i>)
	 * @param tagDefinitionMap
	 *            definitions of the tags to be displayed in this
	 *            {@link DomImageViewer} (never <i>null</i>)
	 * @param width
	 *            the width of this {@link DomImageViewer} (or <i>null</i> if
	 *            the width shall be adaptive)
	 */
	public DomImageViewer(List<? extends IResource> images, DomImageViewerTagDefinitionMap tagDefinitionMap, ICssLength width) {

		this.images = Objects.requireNonNull(images);
		this.tagDefinitionMap = Objects.requireNonNull(tagDefinitionMap);
		this.toolBar = new DomImageViewerToolBar(this);
		this.canvas = new DomImageViewerCanvas(this);
		this.widthDefined = width != null;
		this.zoomLevel = DomImageViewerZoomLevel.getDefault();
		this.rotation = DomImageViewerRotation._0;
		this.pageIndex = 0;
		this.tagsDisplayed = true;

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER);
		Optional.ofNullable(width).ifPresent(it -> setStyle(CssStyle.WIDTH, it));

		appendChild(toolBar);
		appendChild(canvas);

		refresh();
	}

	boolean isWidthDefined() {

		return widthDefined;
	}

	// ------------------------------ zoom ------------------------------ //

	/**
	 * Returns the current {@link DomImageViewerZoomLevel}.
	 *
	 * @return the {@link DomImageViewerZoomLevel} (never <i>null</i>)
	 */
	public DomImageViewerZoomLevel getZoomLevel() {

		return zoomLevel;
	}

	/**
	 * Changes the {@link DomImageViewerZoomLevel}.
	 *
	 * @param zoomLevel
	 *            the new {@link DomImageViewerZoomLevel} (never <i>null</i>)
	 */
	public void setZoomLevel(DomImageViewerZoomLevel zoomLevel) {

		this.zoomLevel = Objects.requireNonNull(zoomLevel);

		toolBar.refresh();
		canvas.applyTransformations();
	}

	/**
	 * Zooms in to the next {@link DomImageViewerZoomLevel}.
	 * <p>
	 * If the maximum {@link DomImageViewerZoomLevel} is already reached,
	 * nothing will happen.
	 */
	public void zoomIn() {

		setZoomLevel(zoomLevel.getNext());
	}

	/**
	 * Zooms out to the previous {@link DomImageViewerZoomLevel}.
	 * <p>
	 * If the minimum {@link DomImageViewerZoomLevel} is already reached,
	 * nothing will happen.
	 */
	public void zoomOut() {

		setZoomLevel(zoomLevel.getPrevious());
	}

	// ------------------------------ page ------------------------------ //

	/**
	 * Returns the number of available pages.
	 *
	 * @return the page count
	 */
	public int getPageCount() {

		return images.size();
	}

	/**
	 * Returns the image displayed on the current page, as an {@link IResource}.
	 *
	 * @return the image on the current page (never <i>null</i>)
	 */
	public IResource getPageImage() {

		return images.get(pageIndex);
	}

	/**
	 * Returns the zero-based index of the current page.
	 *
	 * @return the current page index
	 */
	public int getPageIndex() {

		return pageIndex;
	}

	/**
	 * Shows the image on the next page.
	 */
	public void showNextPage() {

		pageIndex++;
		refresh();
	}

	/**
	 * Shows the image on the previous page.
	 */
	public void showPreviousPage() {

		pageIndex--;
		refresh();
	}

	// ------------------------------ rotation ------------------------------ //

	/**
	 * Returns the current {@link DomImageViewerRotation}.
	 *
	 * @return the {@link DomImageViewerRotation} (never <i>null</i>)
	 */
	public DomImageViewerRotation getRotation() {

		return rotation;
	}

	/**
	 * Rotates the view to the next {@link DomImageViewerRotation}.
	 */
	public void rotateRight() {

		this.rotation = rotation.getNext();
		canvas.applyTransformations();
	}

	/**
	 * Rotates the view to the previous {@link DomImageViewerRotation}.
	 */
	public void rotateLeft() {

		this.rotation = rotation.getPrevious();
		canvas.applyTransformations();
	}

	// ------------------------------ tags ------------------------------ //

	/**
	 * Toggles tags being displayed.
	 */
	public void toggleTags() {

		this.tagsDisplayed = !tagsDisplayed;
		refresh();
	}

	/**
	 * Returns the {@link DomImageViewerTagDefinition} instances for the current
	 * page.
	 * <p>
	 * Returns an empty {@link Collection} if tags shall currently not be
	 * displayed.
	 *
	 * @return the {@link DomImageViewerTagDefinition} instances for the current
	 *         page (never <i>null</i>)
	 */
	Collection<DomImageViewerTagDefinition> getPageTagDefinitions() {

		return tagsDisplayed? tagDefinitionMap.getOrDefault(pageIndex, new ArrayList<>()) : new ArrayList<>();
	}

	// ------------------------------ internal ------------------------------ //

	private void refresh() {

		toolBar.refresh();
		canvas.refresh();
	}
}
