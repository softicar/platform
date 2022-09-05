package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;

/**
 * @author Oliver Richers
 */
public class DomIcon extends DomImage {

	public DomIcon(IResource imageResource) {

		super(imageResource);
		setCssClass(DomCssClasses.DOM_ICON);
	}
}
