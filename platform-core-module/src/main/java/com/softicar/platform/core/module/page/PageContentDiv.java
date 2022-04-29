package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.page.navigation.PageNavigationMarker;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.page.IEmfPage;

/**
 * Container element for the actual page content, as returned by
 * {@link IEmfPage#createContentNode}.
 *
 * @author Oliver Richers
 */
class PageContentDiv extends DomDiv {

	public PageContentDiv(PageNavigationLink<?> link) {

		addCssClass(PageCssClasses.PAGE_CONTENT_DIV);
		addMarker(PageNavigationMarker.PAGE_CONTENT_DIV);
		appendChild(link.createContentNode());
		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			addCssClass(PageCssClasses.TEST_SYSTEM);
		}
	}
}
