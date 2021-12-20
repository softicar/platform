package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import com.softicar.platform.core.module.page.navigation.PageNavigationCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.dom.elements.DomDiv;

public class PageDiv extends DomDiv {

	private final PageHeaderAndContentDiv pageHeaderAndContentDiv;
	private boolean navigationOpen = true;
	private final PageNavigationDiv navigationDiv;

	public PageDiv(IAjaxDocumentParameters documentParameters) {

		this.pageHeaderAndContentDiv = new PageHeaderAndContentDiv(this::collapseOrExpandNavigation);
		this.navigationDiv = new PageNavigationDiv(pageHeaderAndContentDiv);

		appendChild(navigationDiv);
		appendChild(pageHeaderAndContentDiv);

		checkDocumentParameters(documentParameters);

		setCssClass(PageCssClasses.PAGE_DIV);
	}

	private void checkDocumentParameters(IAjaxDocumentParameters documentParameters) {

		PageParameterParser parameterParser = new PageParameterParser(documentParameters);
		navigationDiv.getPageController().showPage(parameterParser.getPage(), parameterParser.getModuleInstanceId());
	}

	private void collapseOrExpandNavigation() {

		this.navigationOpen = !navigationOpen;

		if (navigationOpen) {
			removeCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_COLLAPSED);
		} else {
			addCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_COLLAPSED);
		}
	}
}
