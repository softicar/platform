package com.softicar.platform.emf.page.external;

import com.softicar.platform.dom.elements.DomIFrame;
import com.softicar.platform.emf.EmfCssClasses;

/**
 * A {@link DomIFrame} for a given URL.
 *
 * @author Oliver Richers
 */
public class EmfExternalPageFrame extends DomIFrame {

	public EmfExternalPageFrame(String url) {

		setCssClass(EmfCssClasses.EMF_EXTERNAL_PAGE_FRAME);
		setAddress(url);
	}
}
