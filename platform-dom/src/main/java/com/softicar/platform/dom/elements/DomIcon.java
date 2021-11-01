package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.resource.IResource;

/**
 * @author Oliver Richers
 */
public class DomIcon extends DomImage {

	public DomIcon(IResource imageResource) {

		super(imageResource);
		setCssClass(DomElementsCssClasses.DOM_ICON);
	}
}
