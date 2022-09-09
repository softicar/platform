package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.page.IEmfPageContentElement;
import java.util.Optional;

/**
 * Container element for the actual page content, as returned by
 * {@link IEmfPage#createContentNode}.
 *
 * @author Oliver Richers
 */
public class PageContentDiv extends DomDiv implements IEmfPageContentElement, IDomPopupContext {

	private final Optional<IEmfPage<?>> page;

	public PageContentDiv(PageNavigationLink<?> link) {

		this.page = Optional.ofNullable(link.getPage());

		addCssClass(CoreCssClasses.PAGE_CONTENT_DIV);
		addMarker(CoreTestMarker.PAGE_NAVIGATION_PAGE_CONTENT_DIV);
		appendChild(link.createContentNode());
		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			addCssClass(CoreCssClasses.TEST_SYSTEM);
		}
	}

	@Override
	public Optional<IEmfPage<?>> getPage() {

		return page;
	}
}
