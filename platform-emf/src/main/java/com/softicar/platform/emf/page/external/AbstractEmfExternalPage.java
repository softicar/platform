package com.softicar.platform.emf.page.external;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.IEmfPage;

/**
 * Extend this class to embed an external page.
 *
 * @author Oliver Richers
 */
public abstract class AbstractEmfExternalPage<I extends IEmfModuleInstance<I>> implements IEmfPage<I> {

	@Override
	public IDomNode createContentNode(I moduleInstance) {

		DomDiv contentDiv = new DomDiv();
		contentDiv.addCssClass(EmfCssClasses.EMF_EXTERNAL_PAGE_FRAME_CONTAINER);
		contentDiv.appendChild(new EmfExternalPageFrame(getExternalPageUrl(moduleInstance)));
		return contentDiv;
	}

	protected abstract String getExternalPageUrl(I moduleInstance);
}
