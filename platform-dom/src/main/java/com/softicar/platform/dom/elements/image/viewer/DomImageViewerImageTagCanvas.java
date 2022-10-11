package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTag;

/**
 * An invisible element that contains a {@link DomImageViewerImage} child, and
 * {@link DomImageViewerTag} children.
 * <p>
 * Has the exact same size as the {@link DomImageViewerImage} child, which
 * allows for precise absolute positioning of the contained
 * {@link DomImageViewerTag} children.
 *
 * @author Alexander Schmidt
 */
public class DomImageViewerImageTagCanvas extends DomDiv {

	public DomImageViewerImageTagCanvas() {

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TAG_CANVAS);
	}
}
